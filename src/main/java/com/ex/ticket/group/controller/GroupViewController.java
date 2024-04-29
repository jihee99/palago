package com.ex.ticket.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class GroupViewController {

    @GetMapping("/api/group/{groupId}/home")
    public ModelAndView groupHomeByGroupId(
            @PathVariable(name = "groupId") String groupId,
            Model model
    ){
        ModelAndView modelAndView = new ModelAndView("group/home");
        modelAndView.addObject("groupId", groupId);
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

    @GetMapping("/api/group/{groupId}/event/{eventId}/detail")
    public ModelAndView manageEventDetail(
            @PathVariable(name = "groupId") String groupId,
            @PathVariable(name = "eventId") String eventId
    ){
        ModelAndView modelAndView = new ModelAndView("group/event/detail");
        System.out.println("################manageEventDetail###############");
        System.out.println(groupId + "   " + eventId);

        modelAndView.addObject("groupId", groupId);
        modelAndView.addObject("eventId", eventId);

        return modelAndView;
    }
}
