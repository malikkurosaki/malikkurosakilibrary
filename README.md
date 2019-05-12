# Ambil Data Dari Firestore ke csv


__langsung ke ritualnya__


```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

___jangan sampe lupa ini juga___

```java
dependencies {
	        implementation 'com.github.malikkurosaki:malikkurosakilibrary:2.0'
	}
```

### cara penggunaan

__permisinya dulu__

```xml
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.INTERNET"/>
```

__pengaplikasian di activity__

```java
 db = FirebaseFirestore.getInstance();
        // minta permisi , kode disamain aja
        MalikFireStoreToCsv.mintaIjinBacaTulisExternal(this,123);

        // new object deklarasi setting
        MalikFireStoreToCsv storeToCsv = new MalikFireStoreToCsv(this)
                .firestoreNya(db) // object firestore
                .namaReference("data") // nama colectionnnya , kl beringkan Gunakan ("tingkat1/tingkat2/tingkat3") sampe yang terakhir
                .namaFilenya("malik hasil"); // nama file setelah tersimpan

        // mulai menyimpan
        storeToCsv.mulai();
```

__result permisinys__

```java
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"ijin telah diberikan , silahkan lanjutkan",Toast.LENGTH_LONG).show();
            }
        }
    }
```    


__donasi ke : wa 081338929722__
