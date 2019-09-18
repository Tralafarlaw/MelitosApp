package com.amuyu.melitos.ui.vender;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amuyu.melitos.C;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VentasViewModel extends ViewModel {
    MutableLiveData<List<VentasItem>> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public MutableLiveData<List<VentasItem>> getItems() {
        if (items == null){
            items = new MutableLiveData<>();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String key = auth.getUid();
            final CollectionReference reference = db.collection(C.UID).document(key)
                    .collection(C.cProducto);
            reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        List<VentasItem> itemsa = new ArrayList<>();
                        for(QueryDocumentSnapshot doc: task.getResult()){
                            VentasItem v = new VentasItem(doc.getData());
                            itemsa.add(v);
                        }
                        items.setValue(itemsa);
                    }
                }
            });
        }
        return items;
    }




}
