package com.springboot.cli.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author l
 */
@Data
@Accessors(chain = true)
public class JwtUser {

    private boolean valid;
    private String userId;
    private String role;

    public JwtUser() {
        this.valid = false;
    }


}
