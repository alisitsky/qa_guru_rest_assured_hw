package models;

import lombok.Data;

@Data
public class CreateUserReqResBodyModel {
    String name, job, id, createdAt;
}