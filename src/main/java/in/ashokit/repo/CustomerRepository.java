package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Customer;

public interface CustomerRepository  extends JpaRepository<Customer,Integer>{
	
	public Customer findByUname(String uname);

}
