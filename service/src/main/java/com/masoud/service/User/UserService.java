package com.masoud.service.User;

import com.masoud.Utils.JWTUtil;
import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.comman.utils.HashUtil;
import com.masoud.dataaccess.entity.user.Customer;
import com.masoud.dataaccess.entity.user.Role;
import com.masoud.dataaccess.entity.user.User;
import com.masoud.dataaccess.repository.user.CustomerRepository;
import com.masoud.dataaccess.repository.user.RoleRepository;
import com.masoud.dataaccess.repository.user.UserRepository;
import com.masoud.dto.user.*;
import com.masoud.service.base.CRUDService;
import com.masoud.service.base.HasValidation;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;



@Service
public class UserService implements CRUDService<UserDto>, HasValidation<UserDto> {

        private final UserRepository repository;
        private final JWTUtil jwtUtil;
        private final RoleRepository roleRepository;
        private final CustomerRepository customerRepository;
        private final ModelMapper mapper;
@Autowired
    public UserService(UserRepository userRepository, JWTUtil jwtUtil, RoleRepository roleRepository, ModelMapper modelmapper, CustomerRepository customerRepository, ModelMapper mapper) {
        this.repository = userRepository;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    this.customerRepository = customerRepository;
    this.mapper = mapper;

    }


    public LimitUserDto login(LoginDto dto) throws NotFoundExceptions, ValidationExceptions {
       String password = dto.getPassword();
       password= HashUtil.hashWithSHA256(password);
       User user = repository.findFirstByUsernameEqualsIgnoreCaseAndPassword(dto.getUsername(), password).orElseThrow(NotFoundExceptions::new);
       if(!user.getEnabled())
       {
           throw new ValidationExceptions("your user is disabel pleas contact a soport ");
       }
       LimitUserDto result= mapper.map(user, LimitUserDto.class);
       result.setToken(jwtUtil.generateToken(result.getUsername()));
       return result;

    }

    @SneakyThrows
    public UserDto readuserbyusername(String username)
    {
        User user = repository.findFirstByUsername(username).orElseThrow(NotFoundExceptions::new );
        return mapper.map(user, UserDto.class);

    }
    @SneakyThrows
    public UserDto read(Long id)
    {
        User user = repository.findById(id).orElseThrow(NotFoundExceptions::new );
        return mapper.map(user, UserDto.class);

    }


    @Override
    public UserDto create(UserDto dto) throws ValidationExceptions {
        checkValidation(dto);
        Optional<User> olduser= repository.findFirstByUsername(dto.getUsername());
        if(olduser.isPresent())
        {
            throw new ValidationExceptions("username already exists pleas login");
        }

       Customer customer= customerRepository.save(mapper.map(dto.getCustomer(),Customer.class));
        User user=mapper.map(dto,User.class);
        user.setCustomer(customer);
        user.setPassword(HashUtil.hashWithSHA256(user.getPassword()));
        user.setRigesterdate(LocalDateTime.now());
        user.setEnabled(true);

        Optional<Role> userRole =roleRepository.findFirstByNameEqualsIgnoreCase("user");
        if(userRole.isPresent())
        {
            HashSet<Role> roles=new HashSet<>();
            roles.add(userRole.get());     // این تکه کد تحقیق شود
            user.setRoles(roles);
        }

       User savedUsr= repository.save(user);
        return mapper.map(savedUsr,UserDto.class);

    }
    @Override
    public Page<UserDto> readAll(Integer page, Integer size)
    {
        if(page == null)
        {
            page = 0;
        }
        if(size==null)
        {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page)).
                map(x->mapper.map(x,UserDto.class));

    }


    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }


    @Override
    public UserDto update(UserDto dto) throws Exception {
        checkValidation(dto);
        if (dto.getId() == null||dto.getId()<=0)
        {
            throw new ValidationExceptions("id is required");
        }
        User olddata=repository.findById(dto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setPhone(Optional.ofNullable(dto.getPhone()).orElse(olddata.getPhone()));
        olddata.setEmail(Optional.ofNullable(dto.getEmail()).orElse(olddata.getEmail()));
        olddata.setEnabled(Optional.ofNullable(dto.getEnabled()).orElse(olddata.getEnabled()));
        if(dto.getCustomer()!=null) {
            olddata.setCustomer(Optional.ofNullable(mapper.map(dto.getCustomer(), Customer.class)).orElse(olddata.getCustomer()));
        }
        repository.save(olddata);
        return mapper.map(olddata,UserDto.class);

    }

    public UserDto changePasswordByAdmin(UserDto dto) throws Exception {

        if (dto.getId() == null||dto.getId()<=0)
        {
            throw new ValidationExceptions("id is required");
        }
        if(dto.getPassword()==null||dto.getPassword().isEmpty())
        {
            throw new ValidationExceptions("password is required");
        }

        User olddata=repository.findById(dto.getId()).orElseThrow(NotFoundExceptions::new);
        String password=HashUtil.hashWithSHA256(dto.getPassword());
        olddata.setPassword(password);
        repository.save(olddata);
        return mapper.map(olddata,UserDto.class);

    }
    public UserDto ChangePassByUser(ChangePassDto dto,UserDto userDto) throws Exception {
        if (dto == null)
        {
            throw new ValidationExceptions("please fill a data");
        }
        if(dto.getOldPass()==null||dto.getOldPass().isEmpty())
        {
            throw new ValidationExceptions("oldPass is required");
        }
        if (dto.getNewPass()==null||dto.getNewPass().isEmpty())
        {
            throw new ValidationExceptions("newPass is required");
        }
        if (dto.getNewPass2()==null||dto.getNewPass2().isEmpty())
        {
            throw new ValidationExceptions("newPass2 is required");
        }
        if(!dto.getNewPass().equals(dto.getNewPass2()))
        {
            throw new ValidationExceptions("new pass and Repeated do not match");
        }
        User user = repository.findById(userDto.getId()).orElseThrow(NotFoundExceptions::new);
        if(!user.getPassword().equals(HashUtil.hashWithSHA256(dto.getOldPass())))
        {
            throw new ValidationExceptions("Increasing password");
        }
        user.setPassword(HashUtil.hashWithSHA256(dto.getNewPass()));
        repository.save(user);
        return userDto;



    }
    public UserDto updateProfile(UpdateProfileDto dto) throws Exception {

        if(dto.getFirstname() == null|| dto.getFirstname().isEmpty())
        {
            throw new ValidationExceptions("plese enter first name");
        }
        if(dto.getLastname() == null|| dto.getLastname().isEmpty())
        {
            throw new ValidationExceptions("plese enter last name");
        }
        if(dto.getEmail() == null|| dto.getEmail().isEmpty())
        {
            throw new ValidationExceptions("plese enter email");
        }
        if(dto.getMobile() == null|| dto.getMobile().isEmpty())
        {
            throw new ValidationExceptions("plese enter mobile");
        }
        if(dto.getTel() == null|| dto.getTel().isEmpty())
        {
            throw new ValidationExceptions("plese enter telephone");
        }
        if(dto.getAddress() == null|| dto.getAddress().isEmpty())
        {
            throw new ValidationExceptions("plese enter address");
        }
        if(dto.getPostalcode() == null|| dto.getPostalcode().isEmpty())
        {
            throw new ValidationExceptions("plese enter postal code");
        }
        User olddata=repository.findById(dto.getId()).orElseThrow(NotFoundExceptions::new);
        olddata.setPhone(Optional.ofNullable(dto.getMobile()).orElse(olddata.getPhone()));
        olddata.setEmail(Optional.ofNullable(dto.getEmail()).orElse(olddata.getEmail()));
        olddata.getCustomer().setAddress(Optional.ofNullable(dto.getAddress()).orElse(olddata.getCustomer().getAddress()));
        olddata.getCustomer().setTel(Optional.ofNullable(dto.getTel()).orElse(olddata.getCustomer().getTel()));
        olddata.getCustomer().setFirstname(Optional.ofNullable(dto.getFirstname()).orElse(olddata.getCustomer().getFirstname()));
        olddata.getCustomer().setLastname(Optional.ofNullable(dto.getLastname()).orElse(olddata.getCustomer().getLastname()));
        olddata.getCustomer().setPostalcode(Optional.ofNullable(dto.getPostalcode()).orElse(olddata.getCustomer().getPostalcode()));
        repository.save(olddata);
        return mapper.map(olddata,UserDto.class);

    }


    @Override
    public void checkValidation(UserDto dto) throws ValidationExceptions {
        if(dto.getCustomer()==null)
        {
            throw new ValidationExceptions("pleas enter customer information ");
        }
        if(dto.getCustomer().getFirstname() == null|| dto.getCustomer().getFirstname().isEmpty())
        {
            throw new ValidationExceptions("plese enter first name");
        }
        if(dto.getCustomer().getLastname() == null|| dto.getCustomer().getLastname().isEmpty())
        {
            throw new ValidationExceptions("plese enter last name");
        }
        if(dto.getUsername() == null|| dto.getUsername().isEmpty())
        {
            throw new ValidationExceptions("plese enter user name");
        }
        if(dto.getPassword() == null|| dto.getPassword().isEmpty())
        {
            throw new ValidationExceptions("plese enter password");
        }
        if(dto.getEmail() == null|| dto.getEmail().isEmpty())
        {
            throw new ValidationExceptions("plese enter email");
        }
        if(dto.getPhone() == null|| dto.getPhone().isEmpty())
        {
            throw new ValidationExceptions("plese enter mobile");
        }
        if(dto.getCustomer().getTel() == null|| dto.getCustomer().getTel().isEmpty())
        {
            throw new ValidationExceptions("plese enter telephone");
        }
        if(dto.getCustomer().getAddress() == null|| dto.getCustomer().getAddress().isEmpty())
        {
            throw new ValidationExceptions("plese enter address");
        }
        if(dto.getCustomer().getPostalcode() == null|| dto.getCustomer().getPostalcode().isEmpty())
        {
            throw new ValidationExceptions("plese enter postal code");
        }
    }



}
