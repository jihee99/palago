package com.ex.ticket.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class CommonGroupController {

    @GetMapping("/api/group/{groupId}")
    public ModelAndView groupHomeByGroupId(
            @PathVariable(name = "groupId") String groupId,
            Model model
    ){
        ModelAndView modelAndView = new ModelAndView("group/event/list");
        modelAndView.addObject("groupId", groupId);
        System.out.println("################groupHomeByGroupId###############");
        System.out.println("groupHomeByGroupId" + groupId);

        return modelAndView;
    }

    @GetMapping("/api/group/{groupId}/event")
    public ModelAndView manageEvent(
            @PathVariable(name = "groupId") String groupId
    ){
        ModelAndView modelAndView = new ModelAndView("group/event/list");
        System.out.println("################manageEvent###############");
        System.out.println(groupId);
        modelAndView.addObject("groupId", groupId);
        return modelAndView;
    }
}
