package models;

import lombok.Data;

@Data
public class UnsuccessfulRegisterReqResBodyModel {
    String email, error;
}