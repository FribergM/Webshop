package se.iths.friberg.webshop.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.friberg.webshop.db.entities.User;
import se.iths.friberg.webshop.db.repositories.UserRepository;
import se.iths.friberg.webshop.models.MyUserDetails;

@Service
public class JpaUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepo;
    
    public JpaUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepo.findByUsername(username);
        
        if(user == null){
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        return new MyUserDetails(user);
    }
}
