package com.echallenge.config;

import com.echallenge.entity.Candidate;
import com.echallenge.entity.QuestionType;
import com.echallenge.entity.User;
import com.echallenge.repository.CandidateRepository;
import com.echallenge.repository.QuestionTypeRepository;
import com.echallenge.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               CandidateRepository candidateRepository,
                               QuestionTypeRepository questionTypeRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@echallenge.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@echallenge.com");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                log.info("Seeded default admin account: admin@echallenge.com / admin123");
            }

            if (userRepository.findByEmail("candidat@echallenge.com").isEmpty()) {
                Candidate demo = new Candidate();
                demo.setEmail("candidat@echallenge.com");
                demo.setPasswordHash(passwordEncoder.encode("candidat123"));
                demo.setRole(User.Role.CANDIDATE);
                demo.setFirstName("Karim");
                demo.setLastName("Demo");
                demo.setSchool("ENSA");
                demo.setField("Génie Informatique");
                demo.setGsm("0600000000");
                demo.setCode("CAND-DEMO");
                demo.setConfirmed(true);
                candidateRepository.save(demo);
                log.info("Seeded demo candidate account: candidat@echallenge.com / candidat123");
            }

            if (questionTypeRepository.count() == 0) {
                questionTypeRepository.save(new QuestionType("QCM", QuestionType.Type.SINGLE_CHOICE));
                questionTypeRepository.save(new QuestionType("Choix multiples", QuestionType.Type.MULTIPLE_CHOICE));
                questionTypeRepository.save(new QuestionType("Vrai/Faux", QuestionType.Type.TRUE_FALSE));
                log.info("Seeded default question types");
            }
        };
    }
}
