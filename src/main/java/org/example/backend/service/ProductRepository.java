package org.example.backend.service;


import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;
import org.example.backend.Invoicer;
import org.example.backend.Product;

@Repository(forEntity = Product.class)
public interface ProductRepository extends EntityRepository<Product, Long> {

    public List<Product> findByInvoicer(Invoicer invoicer);

    public QueryResult findByInvoicerAndNameLikeIgnoreCase(Invoicer invoicer,
            String string);
}