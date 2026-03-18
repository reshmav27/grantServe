package com.cts.grantserve.projection;
import org.springframework.beans.factory.annotation.Value;


public interface IUserProjection {

        Long getUserID();
        String getName();
        String getEmail();
        String getRole();
        String getStatus();


        @Value("#{target.name + ' (' + target.role + ')'}")
        String getUserSummary();
}

