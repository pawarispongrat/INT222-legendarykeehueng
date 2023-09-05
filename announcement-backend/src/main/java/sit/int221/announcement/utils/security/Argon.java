package sit.int221.announcement.utils.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon {

    private final Argon2PasswordEncoder encoder;
    private final int SALT_LENGTH = 16;

    public Argon() {
        int ITERATIONS = 2;
        int MEMORY = 2048;
        int PARALLELISM = 1;
        int HASH_LENGTH = 16;
        this.encoder = new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);
    }
    public Argon(int iterations,int memory,int hashLength,int parallelism) {
        this.encoder = new Argon2PasswordEncoder(SALT_LENGTH,hashLength,parallelism,memory,iterations);
    }

    public String encode(String password) {
        return this.encoder.encode(password);
    }

    public boolean match(String rawPassword,String encodedPassword) {
        return this.encoder.matches(rawPassword,encodedPassword);
    }
}
