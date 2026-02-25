package com.ecommerce.project.controller;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.RefreshToken;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RefreshTokenRepository;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.securityjwt.JwtUtils;
import com.ecommerce.project.securityjwt.RefreshTokenService;
import com.ecommerce.project.securityjwt.request.LoginRequest;
import com.ecommerce.project.securityjwt.request.SignupRequest;
import com.ecommerce.project.securityjwt.response.MessageResponse;
import com.ecommerce.project.securityjwt.response.UserInfoResponse;
import com.ecommerce.project.securityjwt.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
        }catch (AuthenticationException e){
            Map<String,Object> map=new HashMap<>();
            map.put("message","Bad credentials");
            map.put("status",false);
            map.put("localtimestamp", LocalDateTime.now());
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        ResponseCookie cookie = jwtUtils.generateRefreshCookie(refreshToken.getToken());
        List<String> roles = userDetails.
                getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getId(),userDetails.getUsername(),jwtCookie.toString(),roles,LocalDateTime.now());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(userInfoResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signuprequest){
        if(userRepository.existsByUsername(signuprequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken !!"));
        }

        if(userRepository.existsByEmail(signuprequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken !!"));
        }
        User user = new User(
                signuprequest.getUsername(),
                signuprequest.getEmail(),
                passwordEncoder.encode(signuprequest.getPassword())
        );

        Set<String> setRoles = signuprequest.getRoles();
        Set<Role> roles=new HashSet<>();

        if(setRoles == null){
            Role userRole=roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException(("Error: Role is not found")));
            roles.add(userRole);
        }else {
            setRoles.forEach(role->{
                switch (role){
                    case "admin":
                       Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                               .orElseThrow(()->new RuntimeException(("Error: Role is not found")));
                               roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(()->new RuntimeException(("Error: Role is not found")));
                                roles.add(sellerRole);
                        break;
                    default:
                        Role userRole=roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException(("Error: Role is not found")));

                            roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered Successfully"));
    }

    @GetMapping("/username")
    public String currentUsername(Authentication authentication){
        if(authentication !=null)
           return authentication.getName();
        else
            return "";
    }

    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(Authentication authentication){
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles =userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toList();

            UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles, LocalDateTime.now());

            return ResponseEntity.ok().body(userInfoResponse);
        }

        return new ResponseEntity<>("JWT Cookie is Invalid or Expired",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token missing");
        }

        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // USE YOUR UTILS instead of hardcoding "jwt" and "/api"
                    // This ensures the UserDetails are correct for the new token
                    UserDetailsImpl userDetails = UserDetailsImpl.build(user);
                    ResponseCookie newJwtCookie = jwtUtils.generateJwtCookie(userDetails);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                            .body(new MessageResponse("Token refreshed successfully"));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signout(HttpServletRequest request){
        String refreshTokenFromCookie = jwtUtils.getRefreshTokenFromCookie(request);

        if(refreshTokenFromCookie !=null){
            refreshTokenRepository.findByToken(refreshTokenFromCookie)
                    .ifPresent(refreshTokenRepository::delete);
        }
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie cleanRefreshCookie = jwtUtils.getCleanRefreshCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
                .header(HttpHeaders.SET_COOKIE,cleanRefreshCookie.toString()).body(new MessageResponse("You've been signed out!"));

    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauthSuccess(){
        return ResponseEntity.ok("Google Login SuccessFul ! Cookie have been set in Postman/Browser.");
    }
}
