package com.controller;


import com.config.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.*;
import com.service.*;
import com.service.dto.CartDetailsDto;
import com.service.impl.CartServicesImpl;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Order;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiControlller {

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserService userService;

    @Autowired
    public ProductService productService;
    @Autowired
    CartServicesImpl cartServices;

    @Autowired
    DistrictService districtService;

    @Autowired
    WardService wardService;

    @Autowired
    OrderService orderService;

    @Autowired
    AddressService addressService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("addItemToCart/{id}/{quantity}")
    public Object addItemToCart(@CookieValue("user_login") String userLogin, @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(userLogin));

        Product product = productService.findById(id);

        CartdetailId cartdetailId = new CartdetailId();
        cartdetailId.setCartId(u.getUserCart().getId());
        cartdetailId.setProductId(product);

        Cartdetail prevItemline = cartServices.findItemLineById(cartdetailId);

        if (prevItemline == null) {
            prevItemline = new Cartdetail();
            prevItemline.setQuantity(1);
        } else
            prevItemline.setQuantity(prevItemline.getQuantity() + 1);
        prevItemline.setId(cartdetailId);

        ResponseData responseData = new ResponseData("success", null);

        if (cartServices.addItemLine(prevItemline) == null) {
            responseData.setResult("error");
            responseData.setMessage("server got error: code 500");
        }

        return responseData;
    }

    @GetMapping("deleteItemInCart/{id}")
    public Object deleteItemInCart(@CookieValue("user_login") String userLogin, @PathVariable Long id) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(userLogin));

        Product product = productService.findById(id);

        CartdetailId cartdetailId = new CartdetailId();
        cartdetailId.setCartId(u.getUserCart().getId());
        cartdetailId.setProductId(product);

        ResponseData responseData = new ResponseData("error", null);
        if (cartServices.deleteItemLine(cartdetailId) == true) {
            responseData.setResult("success");
        }
        return responseData;
    }

    @GetMapping("lineItemChangeQuantity/{pId}/{number}")
    public Object lineItem(@CookieValue("user_login") String userLogin, @PathVariable("number") Integer number, @PathVariable("pId") Long id) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(userLogin));
        Product product = productService.findById(id);

        CartdetailId cartdetailId = new CartdetailId();
        cartdetailId.setCartId(u.getUserCart().getId());
        cartdetailId.setProductId(product);

        Cartdetail cartdetail = new Cartdetail();
        cartdetail.setQuantity(number);
        cartdetail.setId(cartdetailId);

        ResponseData responseData = new ResponseData("success", null);

        if (cartServices.addItemLine(cartdetail) == null) {
            responseData.setResult("error");
            responseData.setMessage("server got error: code 500");
        }

        return responseData;
    }

    @GetMapping("userCart")
    public Object getUserCart(@CookieValue("user_login") String userLogin) {
        User u = userService.findByUserName(jwtTokenUtil.getUsernameFromToken(userLogin));

        List<CartDetailsDto> lineItems = cartServices.findAllLineItems(u.getUserCart().getId());
        ResponseData responseData;
        responseData = new ResponseData(lineItems, null, null);
        if (lineItems == null || lineItems.isEmpty()) {
            responseData.setResult("error");
        }
        return responseData;
    }

    @GetMapping("getDistricts/{province}")
    public Object getDistrictsByProvince(@PathVariable("province") Integer province) {
        return districtService.findAllByProvince(province);
    }

    @GetMapping("getWards/{district}")
    public Object getWardsByDistrict(@PathVariable("district") Integer district) {
        return wardService.findAllByDistrict(district);
    }

    @GetMapping("product/delete/{id}")
    public Object deleteProduct(@PathVariable("id") Long id) {
        ResponseData responseData;
        if (productService.deleteById(id) != null) {
            responseData = new ResponseData("success", null);
        } else
            responseData = new ResponseData("error", "server got error");
        return responseData;
    }

    @PostMapping("uploadFile")
    public Object uploadImage(@RequestParam(name = "data") MultipartFile file, HttpServletRequest req) throws IOException {
        Date date = Calendar.getInstance().getTime();
        String path = "D:\\Github\\ASS JAVA 5\\FileUpload/image/".concat(date.getYear() + "/" + date.getMonth() + "/");
        List<String> fileUploaded = new ArrayList<String>();
        File directory = new File(path);
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        String contentType = file.getContentType();

        if (contentType != null) {
            String fileName = file.getOriginalFilename();
            contentType = fileName.substring(fileName.indexOf('.'));
            fileName = fileName.replace(contentType, "");

            System.out.println(contentType);
            System.out.println(fileName);

            int i = 0;
            String fileNew = fileName;
            while (new File(path + fileNew + contentType).isFile()) {
                fileNew = fileName + i++;
            }
            String fileUpload = path.concat(fileNew + contentType);
            try {
                file.transferTo(new File(fileUpload));
                fileUploaded.add(fileUpload.replace("D:\\Github\\ASS JAVA 5\\FileUpload", "http://localhost:8080"));
                System.out.println(fileUpload);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("upload error" + e);
            }
        }

        Map<String, Object> res = new HashedMap();
        if (fileUploaded.size() == 0) {
            res.put("result", "error");
            res.put("message", "Cannot upload image file");
        } else {
            res.put("result", "success");
            res.put("data", fileUploaded);
        }
        System.out.println(res);
        return new JSONObject(res).toString();
    }

    @GetMapping("order/address/{id}")
    public Object getOrderAdddess(@PathVariable("id") Long id) {
        return addressService.findById(id);
    }

    @GetMapping("order/getOrderDetails/{orderId}")
    public Object getOrderDetails(@PathVariable("orderId") Long orderId) {
        ResponseData responseData = new ResponseData(orderService.getAllItems(orderId), null, null);
        return responseData;
    }

    @GetMapping("order/calculateRevenueByDate/{date}")
    public Object calculateRevenueByDate(@PathVariable("date") String date) {
        System.out.println("date->" + date);
        System.out.println(date.matches("[1-9]{1}[0-9]{3}-[0-9]{2}-[0-9]{2}"));

        ResponseData responseData = new ResponseData(null, null);
        responseData.setResult("error");
        responseData.setMessage("server got Error");

        BigInteger revenue = orderService.calculateRevenueByDate(date.toString());
        if (revenue != null) {
            responseData.setData(revenue);
            responseData.setResult("success");
            responseData.setMessage(null);
        }
        return responseData;
    }

    @GetMapping("order/calculateRevenueByCategory/{cat}")
    public Object calculateRevenueByCat(@PathVariable("cat") Long cat) {
        ResponseData responseData = new ResponseData(null, null);
        responseData.setResult("error");
        responseData.setMessage("server got Error");

        BigInteger revenue = orderService.calculateRevenueByCategory(cat);
        if (revenue != null) {
            responseData.setData(revenue);
            responseData.setResult("success");
            responseData.setMessage(null);
        }
        return responseData;
    }

    @GetMapping("order/confirm/{orderId}")
    public Object confirmOrder(@PathVariable("orderId") Long orderId) {
        ResponseData responseData = new ResponseData("error", "Server got error");

        if (orderService.confirmOrder(orderId) != null)
            responseData.setResult("success");
        return responseData;
    }

    @GetMapping("order/cancel/{orderId}")
    public Object cancelOrder(@PathVariable("orderId") Long orderId) {
        ResponseData responseData = new ResponseData("error", "Server got error");

        if (orderService.cancelOrder(orderId) != null)
            responseData.setResult("success");
        return responseData;
    }

    @PostMapping("category")
    public Object submitCategory(@RequestBody Category cat) {
        cat.setCar_Products(Collections.emptySet());
        ResponseData responseData = new ResponseData("error", "Server got error");
        cat = categoryService.save(cat);
        if (cat != null && cat.getId() != null) {
            responseData.setResult("success");
            responseData.setData(cat);
        }
        return responseData;
    }

    @GetMapping("category/delete/{id}")
    public Object submitCategory(@PathVariable("id")Long id) {
        ResponseData responseData = new ResponseData("error", "Server got error");
//        if (categoryService.deleteById(id) != null) {
//            responseData.setResult("success");
//        }
        return responseData;
    }
}
