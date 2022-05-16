package uz.pdp.hrmanagementsystem.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.hrmanagementsystem.entities.User;

import java.util.Objects;
import java.util.Optional;


public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal().toString()))
            return Optional.of("self");
        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getId().toString());
    }
}
