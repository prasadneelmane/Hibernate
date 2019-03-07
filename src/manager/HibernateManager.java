package manager;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistryBuilder;

import bean.Employee;

public class HibernateManager {
	SessionFactory sessionFactory = null;
	Session session = null;

	public boolean connect() {
		try {
			System.out.println("Hibernate v 4.3.x");
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(config.getProperties());
			sessionFactory = config.buildSessionFactory(builder.buildServiceRegistry());
			session = sessionFactory.openSession();
			System.out.println("Connected successfully");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	boolean closeSession() {
		if (session != null) {
			session.close();
			sessionFactory.close();
		}
		return true;
	}
	public boolean save(Employee emp) {
		connect();
		try {
			System.out.println("Employee :" + emp.getId());
			session.beginTransaction();
			session.save(emp);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return false;
		} finally {
			closeSession();
		}
		return true;

	}

	public boolean isUserValid(String user) {
		connect();
		Criteria criteria = session.createCriteria(Employee.class);
		Criterion criterion = Restrictions.eq("name", user);
		criteria = criteria.add(criterion);
		Projection projection = Projections.property("name");
		criteria = criteria.setProjection(projection);
		String employee = (String) criteria.uniqueResult();
		System.out.println("=========>" + employee);
		closeSession();
		return employee != null;
	}

	public List<Employee> getAll() {
		return session.createCriteria(Employee.class).addOrder(Order.asc("id")).list();// with sorting
		// return session.createCriteria(Employee.class).list();//without sorting
	}

	public boolean updateUser(int userid, String userName) {
		connect();
		Employee emp = (Employee) session.get(Employee.class, new Integer(userid));
		emp.setName(userName);
		session.beginTransaction();
		session.update(emp);
		session.getTransaction().commit();
		closeSession();
		return true;
	}
	
	public boolean deleteUser(int userId) {
		connect();
		Employee emp = (Employee) session.get(Employee.class, new Integer(userId));
		session.beginTransaction();
		session.delete(emp);
		session.getTransaction().commit();
		closeSession();
		return true;
	}

}
