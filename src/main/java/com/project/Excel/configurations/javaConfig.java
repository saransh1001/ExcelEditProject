package com.project.Excel.configurations;
import com.project.Excel.controllers.excelApplicationController;
import com.project.Excel.dao.jsonDao;
import com.project.Excel.services.excelServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//it is used to mark class as configuration class like xml file
public class javaConfig {

    @Bean
    public excelServiceImplementation excelServiceImplementation() {
        return new excelServiceImplementation();
    }
    @Bean
    public jsonDao jsonDao(){
        return new jsonDao();
    }
}