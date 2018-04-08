package ptr.store.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ptr.store.config.InMemoryConfiguration;
import ptr.store.model.Category;
import ptr.store.model.Customer;
import ptr.store.service.StoreService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryConfiguration.class})
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Transactional
    public void testCreateCategoryByNameOK() {
        // given
        String categoryName = "test Category1";
        // when
        Category categoryCreated = storeService.createCategory(categoryName);
        assertTrue(categoryCreated != null);
        // then
        Collection<Category> categoriesFound = storeService.getCategoriesByName(categoryName);
        assertTrue(categoriesFound.contains(categoryCreated));
    }

    @Test
    @Transactional
    public void testRenameCategoryOK() {
        // given
        givenCategoryNotebooksCreated("Notebooks");
        thenCategoryFound("Notebooks");
        // when
        storeService.renameCategoryName("Notebooks", "Desktops");
        // then
        thenCategoryNotFound("Notebooks");
        thenCategoryFound("Desktops");
    }

    private void thenCategoryNotFound(String categoryName) {
        List<Category> notebookCategories = storeService.getCategoriesByName(categoryName);
        assertTrue(notebookCategories.size() == 0);
    }

    @Test
    @Transactional
    public void testRenameCategoryFails() {
        // given
        givenCategoryNotebooksCreated("Notebooks");
        // when + then
        exception.expect(NoSuchElementException.class);
        exception.expectMessage(String.format(" category name %s not found", "SomeNotebooks"));
        storeService.renameCategoryName("SomeNotebooks", "laptops");
    }

    @Test
    @Transactional
    public void testDeleteCategoryOK() {
        // given
        givenCategoryNotebooksCreated("Notebooks");
        // when
        storeService.deleteCategory("Notebooks");
        // then
        thenCategoryNotFound("Notebooks");
    }

    @Test
    @Transactional
    public void testDeleteCategoryFails() {
        // given
        givenCategoryNotebooksCreated("Notebooks");
        // when
        try {
            exception.expect(NoSuchElementException.class);
            exception.expectMessage(String.format(" category name %s not found", "Some non existing Category Name"));
            storeService.deleteCategory("Some non existing Category Name");
            // then
        } catch (NoSuchElementException noEx) {
            thenCategoryFound("Notebooks");
            throw new NoSuchElementException(String.format(" category name %s not found", "Some non existing Category Name"));
        }
    }

    @Test
    @Transactional
    public void testGetCategoriesByNameOK() {
        // given
        givenCategoryNotebooksCreated("Notebooks");
        givenCategoryNotebooksCreated("Desktops");
        givenCategoryNotebooksCreated("Tablets");
        givenCategoryNotebooksCreated("Mobile phones");
        // when + then
        thenCategoryFound("Notebooks");
        thenCategoryFound("Desktops");
        thenCategoryFound("Tablets");
        thenCategoryFound("Mobile phones");
    }

    @Test
    @Transactional
    public void testGetCategoriesAllOK() {
        // given
        List<Category> savedCategories = Arrays.asList(
                givenCategoryNotebooksCreated("Notebooks"),
                givenCategoryNotebooksCreated("Desktops"),
                givenCategoryNotebooksCreated("Tablets"),
                givenCategoryNotebooksCreated("Mobile phones")
        );
        // when
        List<Category> allCategories = storeService.getCategoriesAll();
        // then
        assertTrue(allCategories != null);
        assertEquals(savedCategories, allCategories);
    }

    @Test
    @Transactional
    public void testCreateCustomerOK() {
        // given
        String customerName = "Test Customer 1";
        String customerEmail = "Test Customer Email 1";
        String contactNumber = "+ 111 222 333 444";
        // when
        Customer customerCreated = storeService.createCustomer(customerName, customerEmail, contactNumber);
        assertTrue(customerCreated != null);
        // then
        Customer customerFound = storeService.findCustomer(customerName, customerEmail, contactNumber);;
        assertTrue(customerFound.equals(customerCreated));
    }

    private void thenCategoryFound(String categoryName) {
        List<Category> notebookCategories = storeService.getCategoriesByName(categoryName);
        assertTrue(notebookCategories.size() == 1);
        assertTrue(notebookCategories.get(0).getName().equals(categoryName));
    }

    private Category givenCategoryNotebooksCreated(String categoryName) {
        Category categoryCreated = storeService.createCategory(categoryName);
        assertTrue(categoryCreated != null);
        return categoryCreated;
    }
}
