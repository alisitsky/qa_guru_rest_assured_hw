package models;

import lombok.Data;

@Data
public class CreateUserReqBodyModel {
    String name, job;
}