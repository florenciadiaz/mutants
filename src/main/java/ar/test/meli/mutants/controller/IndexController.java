package ar.test.meli.mutants.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping(path = "/")
    public String getIndex() {
        return "<h1>Mutants REST API</h1><p>This is a secret project.</p>";
    }

    @GetMapping(path = "/error")
    public String getError() {
        return "<h1>Mutants REST API</h1><p>An error occurred.<br />Please contact the administrator.</p>";
    }

    @GetMapping(path = "/**")
    public String getDefault() {
        return "<h1>Mutants REST API</h1><p>Action not allowed.</p>";
    }
}
