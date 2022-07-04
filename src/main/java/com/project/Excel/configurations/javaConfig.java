package com.project.Excel.configurations;
import com.project.Excel.dao.jsonDao;
import com.project.Excel.services.excelServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//it is used to mark class as configuration class like xml file
//This class is used to behave like XML file and it contains the beans that will be manager by Spring context
public class javaConfig {

    /**
     * This function returns the object of excelServiceImplementation
     * @return excelServiceImplementation object
     */
    @Bean
    public excelServiceImplementation excelServiceImplementation() {
        return new excelServiceImplementation();
    }

    /**
     * This function returns the object of jsonDao
     * @return jsonDao object
     */
    @Bean
    public jsonDao jsonDao(){
        return new jsonDao();
    }
}