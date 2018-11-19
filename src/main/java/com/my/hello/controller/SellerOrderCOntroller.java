package com.my.hello.controller;

import com.my.hello.dto.OrderDTO;
import com.my.hello.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 */
@Controller
@RequestMapping("/seller/order")
public class SellerOrderCOntroller {

    @Autowired
    private OrderMasterService orderMasterService;
    /**
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "1") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        return  new ModelAndView("order/list",map);
    }
}
