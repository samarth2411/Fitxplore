package com.example.fitxplore.myconfig;

import com.example.fitxplore.dao.SubscriberRepository;
import com.example.fitxplore.entity.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SubscriberDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // fetching subscriber by database we have to call dao repository of subscriber

        Subscriber subscriber = subscriberRepository.getSubscriberByUserName(username);
        if (subscriber == null) {
            throw new UsernameNotFoundException("Could not found user with the given username");
        }
        // if the subscriber is not null than pass that subscriber to the customSubscriberDetails class

        CustomSubscriberDetails customSubscriberDetails = new CustomSubscriberDetails(subscriber);
        return customSubscriberDetails;
    }
}
