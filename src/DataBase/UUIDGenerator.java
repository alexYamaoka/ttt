package DataBase;

import DataBase.IdGenerator;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
    @Override
    public String getNewId() {
        return UUID.randomUUID().toString();
    }
}
