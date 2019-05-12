package dev.malikkurosaki.malikkurosakilibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MalikFireStoreToCsv  {

    private FirebaseFirestore db;
    private Context context;
    private Activity activity;
    private String namaRef;
    private String nmFile;

    public MalikFireStoreToCsv (Context masukkanContekNyaDisini){
        this.context = masukkanContekNyaDisini;
        this.activity = (Activity)context;
    }

    public static void mintaIjinBacaTulisExternal(Context context,int codenyaKasi123){
        Activity activity = (Activity)context;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},codenyaKasi123);
        }
    }

    public MalikFireStoreToCsv firestoreNya(FirebaseFirestore masukkanFirestorenyaDisini){
        this.db = masukkanFirestorenyaDisini;
        return this;
    }
    public MalikFireStoreToCsv namaReference(String namaDbReference){
        this.namaRef = namaDbReference;
        return this;
    }
    public MalikFireStoreToCsv namaFilenya(String masukanNamaFilenya){
        this.nmFile = masukanNamaFilenya;
        return this;
    }

   public void mulai(){
        if (db == null || namaRef == null || nmFile == null){
            Toast.makeText(context,"lengkapi sesuai petunjuk",Toast.LENGTH_LONG).show();
            return;
        }
        db.collection(namaRef)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String[]> simpan1 = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : task.getResult()){
                                Map<String,Object> ambil = (HashMap<String, Object>)snapshot.getData();
                                Map<String,Object> terima = new HashMap<>();
                                List<String> data1 = new ArrayList<>();
                                for (Map.Entry<String,Object> entry : ambil.entrySet()){
                                    terima.put(entry.getKey(),entry.getValue());
                                    data1.add(String.valueOf(entry.getValue()));
                                    //Toast.makeText(context,String.valueOf(entry.getValue()),Toast.LENGTH_LONG).show();
                                }

                                simpan1.add(data1.toArray(new String[0]));
                            }

                            try {
                                CSVWriter writer = new CSVWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+nmFile+".csv"));
                                writer.writeAll(simpan1);
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(context,"error gan"+e,Toast.LENGTH_LONG).show();
                            }

                            Toast.makeText(context,"sudah selesai tersimpan di external nama filenya "+nmFile+".csv",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

}
