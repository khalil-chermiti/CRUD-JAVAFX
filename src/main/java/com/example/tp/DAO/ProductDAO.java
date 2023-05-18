package com.example.tp.DAO;

import com.example.tp.models.Product;
import com.example.tp.utils.ConnectionUtil;
import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    @Override
    public boolean addProduct(Product product) {
        try (Session session = ConnectionUtil.getSessionAnnotationFactory().getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(product);
            tx.commit();
            return true;
        } catch (HibernateException he) {
            he.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeProduct(Product product) {
        try (Session session = ConnectionUtil.getSessionAnnotationFactory().getCurrentSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(product);
            tx.commit();
            return true;
        } catch (HibernateException he) {
            he.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> getProducts() {
        try (Session session = ConnectionUtil.getSessionAnnotationFactory().getCurrentSession()) {
            Query result = session.createQuery("from Product");
            return result.getResultList();
        } catch (HibernateException hibernateException) {
            System.out.println("problem fetching products");
            hibernateException.printStackTrace();
            return new ArrayList<Product>();
        }
    }
}
