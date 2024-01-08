package com.sebpot.cyclecompete.controller;

import com.sebpot.cyclecompete.service.ApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/routing")
@RequiredArgsConstructor
public class RoutingController {

    private final ApiService apiService;

    @Value("${routing.API.token}")
    private String routesAPIToken;

    @GetMapping("/mapbox/cycling/{pathString}")
    public Object RedirectToMapBox(
            HttpServletRequest request,
            @PathVariable String pathString,
            @RequestParam("overview") String overview,
            @RequestParam("alternatives") String alternatives,
            @RequestParam("steps") String steps) throws IOException {
        String uri = request.getRequestURI();
        String requestPath = uri.substring(15);
        String redirectUrl = "https://api.mapbox.com/directions/v5" + requestPath
            + "?overview=" + overview
            + "&alternatives=" + alternatives
            + "&steps=" + steps
            + "&access_token=" + routesAPIToken;
        return apiService.redirectRequest(redirectUrl);
    }
}
