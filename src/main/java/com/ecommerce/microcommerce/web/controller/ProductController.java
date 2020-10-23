package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;


    //Récupérer la liste des produits
    @RequestMapping(value = "/Products", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }

    //Récupérer la liste des produits selon l'id
    @RequestMapping(value = "/Products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        System.out.println("Getting Product details for " + id);

        // method core to get student and manage if null
        //List<Product> productList = new ArrayList<Product>();
        //return productDao.stream().filter(product -> product.getId() == id).collect(Collectors.toList());
        return productDao.findById(id);
    }

    @RequestMapping(value = "/Products/delete/{id}", method = RequestMethod.GET)
    public void deleteProduct(@PathVariable int id) {
        System.out.println("Getting Product details for " + id);

        // method core to get student and manage if null
        //List<Product> productList = new ArrayList<Product>();
        //return productDao.stream().filter(product -> product.getId() == id).collect(Collectors.toList());
        productDao.delete(id);
    }


    //Récupérer un produit par son Id
    public Product afficherUnProduit() {
        return null;
    }


    //ajouter un produit
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    public void supprimerProduit() {
    }

    // Mettre à jour un produit
    @RequestMapping(value = "/Products/update/{id}", method = RequestMethod.GET)
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productDao.chercherUnProduitCher(400);
    }



}
