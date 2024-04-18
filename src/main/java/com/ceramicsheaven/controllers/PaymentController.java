package com.ceramicsheaven.controllers;
import com.ceramicsheaven.exceptions.OrderException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Address;
import com.ceramicsheaven.model.Order;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.repositories.OrderRepository;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.responses.PaymentResponse;
import com.ceramicsheaven.services.OrderService;
import com.ceramicsheaven.services.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payment/{paymentMethod}")
    public ResponseEntity<?> createPaymentLink(@RequestBody Address shippingAddress,@PathVariable String paymentMethod, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException, UserException {
        if (paymentMethod.equals("CASH_ON_DELIVERY")){
            User user = userService.findUserProfileByJwt(jwt);
            Order order = orderService.createOrder(user, shippingAddress);

            order.getPaymentDetails().setPaymentMethod(paymentMethod);
            order.getPaymentDetails().setPaymentStatus("PENDING");
            order.setOrderStatus("PLACED");
            orderRepository.save(order);

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderId(order.getId());
            paymentResponse.setOrderItems(order.getOrderItems());
            paymentResponse.setOrderDate(order.getOrderDate());
            paymentResponse.setDeliveryDate(order.getDeliveryDate());
            paymentResponse.setShippingAddress(order.getShippingAddresses());
            paymentResponse.setPaymentMethod(order.getPaymentDetails().getPaymentMethod());
            paymentResponse.setPaymentStatus(order.getPaymentDetails().getPaymentStatus());
            paymentResponse.setTotalPrice(order.getTotalPrice());
            paymentResponse.setTotalDiscountedPrice(order.getTotalDiscountedPrice());
            paymentResponse.setDiscount(order.getDiscount());

//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setMessage("Your Order get placed");
//            apiResponse.setStatus(true);
            return new ResponseEntity<>(paymentResponse,HttpStatus.CREATED);
        }
         {
            try {
                User user = userService.findUserProfileByJwt(jwt);
                Order order = orderService.createOrder(user, shippingAddress);
                RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
                JSONObject paymentLinkRequest = new JSONObject();
                paymentLinkRequest.put("amount",order.getTotalPrice()*100);
                paymentLinkRequest.put("currency","INR");

                JSONObject customer = new JSONObject();
                customer.put("name", order.getUser().getFirstName());
                customer.put("email",order.getUser().getEmail());
                paymentLinkRequest.put("customer",customer);

                JSONObject notify = new JSONObject();
                notify.put("sms",true);
                notify.put("email",true);

                paymentLinkRequest.put("notify",notify);

                paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+order.getId());//FrontEnd URL
                paymentLinkRequest.put("callback_method","get");

                PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

                String paymentLinkId = payment.get("id");
                String paymentLinkUrl = payment.get("Short_url");

                PaymentLinkResponse res = new PaymentLinkResponse();
                res.setPayment_link_id(paymentLinkId);
                res.setPayment_link_url(paymentLinkUrl);

                return new ResponseEntity<PaymentLinkResponse>(res,HttpStatus.CREATED);
            } catch (RazorpayException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id")String paymentId, @RequestParam(name = "order_id")Long orderId) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);

        try {
            Payment payment = razorpayClient.payments.fetch(paymentId);
            if (payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setPaymentStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);

            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Your Order get placed");;
            apiResponse.setStatus(true);

            return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
        }catch (Exception ex){
            throw new RazorpayException(ex.getMessage());
        }
    }
}