package uz.pdp.hrmanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagementsystem.dto.CompanyModel;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.service.CompanyService;
import uz.pdp.hrmanagementsystem.service.JwtUtil;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/company")
public class CompanyController {

    private final CompanyService companyService;

    private final JwtUtil jwtUtil;

    @PutMapping(value = "/create")
    public CompanyModel create(@Valid @RequestBody CompanyModel companyModel, @RequestHeader(name = "Authorization") String token){
        String jwtToken = token.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);
        return companyService.createUserCompany(companyModel,email);
    }

    /*
    DELETE account for SUPER user
    DELETES also all users of this company
     */
    @DeleteMapping(value = "/{id}")
    public GenericResponse deleteCompany(@PathVariable Long id) {
       return companyService.delete(id);
    }





}
