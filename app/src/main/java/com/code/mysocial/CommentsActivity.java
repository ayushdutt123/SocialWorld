package com.code.mysocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.code.mysocial.databinding.ActivityCommentsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.UUID;

public class CommentsActivity extends AppCompatActivity {
    ActivityCommentsBinding binding;
    private String postId;
    private CommentsAdapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        postId=getIntent().getStringExtra("id");
        commentsAdapter=new CommentsAdapter(this);
        binding.recycler.setAdapter(commentsAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        loadComments();
        binding.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=binding.commentEd.getText().toString();
                if (comment.trim().length()>0){
                    comment(comment);
                }
            }
        });
    }
    private void loadComments(){
        FirebaseFirestore.getInstance().collection("Comments").whereEqualTo("postId",postId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                commentsAdapter.clearPosts();
                List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot ds:dsList){
                    CommentModel commentModel=ds.toObject(CommentModel.class);
                    commentsAdapter.addPost(commentModel);
                }
            }
        });
    }
    private void comment(String comment) {
        String id= UUID.randomUUID().toString();
        CommentModel commentModel=new CommentModel(id,postId, FirebaseAuth.getInstance().getUid(),comment);
        FirebaseFirestore.getInstance().collection("Comments").document(id).set(commentModel);
        commentsAdapter.addPost(commentModel);
    }
}