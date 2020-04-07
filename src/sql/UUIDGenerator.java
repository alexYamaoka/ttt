package sql;

import sql.IdGenerator;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
    @Override
    public String getNewId() {
        return UUID.randomUUID().toString();
    }
}
