package bookup.com.google.bookup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BStoreManuallyAddNewBookStep1Activity extends AppCompatActivity {

    private static final String TAG = "BStoreManuallyAddNewBookStep1";

    private static final String KEY_COLLECTION_REF = "users";
    private static final String PATH_USERTYPE = "bookstore";
    private static final String PATH_BOOKSTORE_NAME = "National Bookstore"; //sample
    private static final String PATH_BOOKS = "books";

    private EditText edtTxtISBN;
    private EditText edtTxtTitle;
    private EditText edtTxtAuthor;
    private EditText edtTxtGenre;
    private EditText edtTxtEdition;
    private EditText edtTxtPublisher;
    private EditText edtTxtCoverType;
    private EditText edtTxtPrice;
    private EditText edtTxtSynopsis;

    //instantiate db firestore and get instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bstore_manually_add_new_book_step1);

        refs();
    }

    //widget references from xml
    public void refs()
    {
        edtTxtISBN = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_ISBN);
        edtTxtTitle = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_BookTitle);
        edtTxtAuthor = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Author);
        edtTxtGenre = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Genre);
        edtTxtEdition = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Edition);
        edtTxtPublisher = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Publisher);
        edtTxtCoverType = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_CoverType);
        edtTxtPrice = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Price);
        edtTxtSynopsis = findViewById(R.id.EdtTxtBstoreManuallyAddBookS1_Synopsis);
    }

    //call method on button click
    public void saveBook(View v)
    {
        String ISBN = edtTxtISBN.getText().toString();
        String title = edtTxtTitle.getText().toString();
        String author = edtTxtAuthor.getText().toString();
        String genre = edtTxtGenre.getText().toString();
        String edition = edtTxtEdition.getText().toString();
        String publisher = edtTxtPublisher.getText().toString();
        String coverType = edtTxtCoverType.getText().toString();
        String price = edtTxtPrice.getText().toString();
        String synopsis = edtTxtSynopsis.getText().toString();

        Map<String, Object> bookDetails = new HashMap<>();
        bookDetails.put("ISBN", ISBN);
        bookDetails.put("book title", title);
        bookDetails.put("author", author);
        bookDetails.put("genre", genre);
        bookDetails.put("edition", edition);
        bookDetails.put("publisher", publisher);
        bookDetails.put("cover type", coverType);
        bookDetails.put("price", price);
        bookDetails.put("synopsis", synopsis);

        db.collection(KEY_COLLECTION_REF).document(PATH_USERTYPE).collection(PATH_BOOKSTORE_NAME).document(PATH_BOOKS).collection(genre).document(title).set(bookDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BStoreManuallyAddNewBookStep1Activity.this, "Book Saved!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BStoreManuallyAddNewBookStep1Activity.this, "Error!", Toast.LENGTH_LONG).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
