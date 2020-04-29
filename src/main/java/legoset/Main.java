package legoset;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import legoset.model.Profile;
import legoset.model.LegoSet;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

    private static  EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");

    private static void createLegoSetsAndProfiles() {
        LegoSet[] legoSets = new LegoSet[] {
                new LegoSet("60073", "Service Truck", Year.of(2015), 233),
                new LegoSet("75211", "Imperial TIE Fighter", Year.of(2018), 519),
                new LegoSet("21034", "London", Year.of(2017), 468)
        };
        Profile[] profiles = new Profile[] {
                new Profile("60073", "https://brickset.com/sets/60073-1/Service-Truck", new BigDecimal("4.8")),
                new Profile("75211", "https://brickset.com/sets/75211-1/Imperial-TIE-Fighter", new BigDecimal("3.8")),
                new Profile("21034", "https://brickset.com/sets/21034-1/London", new BigDecimal("4.0"))
        };
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < legoSets.length; ++i) {
                legoSets[i].setProfile(profiles[i]);
                profiles[i].setLegoSet(legoSets[i]);
                em.persist(legoSets[i]);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static List<LegoSet> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM LegoSet l ORDER BY l.profile.avgRating DESC").getResultList();
        } finally {
            em.close();
        }
    }

    private static void deleteAll() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("SELECT l FROM LegoSet l").getResultStream().forEach(em::remove);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    public static void main(String[] args) {
        try {
            createLegoSetsAndProfiles();
            findAll().forEach(legoSet -> System.out.printf("%s %s: %s\n", legoSet.getNumber(), legoSet.getName(), legoSet.getProfile().getAvgRating()));
            deleteAll();
        } finally {
            emf.close();
        }
    }

}
