package com.example.onboarding_project

import com.google.firebase.firestore.*
import java.lang.Exception

class AboutSlide(
    val id: Long,
    val image: String,
    val subtitle: String,
    var targetLink: String,
    val text: String,
    val title: String,
    val number: Long
)

class OnboardingModel {

    fun loadData(
        type: String,
        isAbonent: Boolean,
        idCity: Int,
        onComplete: (arr: ArrayList<AboutSlide>) -> Unit,
        onFailed: (ex: Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false) // отключение кэширования
            .build()
        db.firestoreSettings = settings
        db.collection("onboarding")
            .whereEqualTo("type", type)
            .get()
            .addOnSuccessListener { result ->
                val data = arrayListOf<AboutSlide>()
                val idsSlide = arrayListOf<Long>()
                val checked_list_id_number =
                    HashMap<Long, Long>() //список слайдов без повторений, на случай если будет несколько документов с type

                if(result.isEmpty){
                    onFailed(Exception())
                }
                for (document in result) {
                    //Log.d("exist!!!", "${document.id} => ${document.data}")
                    val idsCity = document.get("idCity") as ArrayList<Long>

                    if ((idsCity.contains(idCity.toLong()))) {
                        if (!((document.getBoolean("isSubscriber") == true) and (!isAbonent))) {
                            val slides = document.get("slides") as ArrayList<HashMap<String, Long>>
                            //val list = slides.get("id_number") as ArrayList<HashMap<String, Long>>
                            for (value1 in slides) {
                                val id = value1["id"]
                                val number = value1["number"]

                                if ((!idsSlide.contains(id)) and (number!! > 0)) {
                                    idsSlide.add(id!!)
                                    checked_list_id_number.put(id, number!!)
                                }

                                db.collection("onboarding").document("slides").collection("slides")
                                    .whereEqualTo("id", id)
                                    .get()
                                    .addOnSuccessListener { documentReference ->
                                        load_slides(documentReference, data, idsSlide, id!!, checked_list_id_number)

                                        //Log.d("slide exist", "DocumentSnapshot added with")
                                        data.sortWith(compareBy({ it.number })) //сортировка по требуемому номеру показа слайда
                                        onComplete(data)
                                    }
                                    .addOnFailureListener { e ->
                                        //Log.w("slide not exist", "Error adding document", e)
                                        onFailed(e)
                                    }
                            }

                        }
                    }
                }

            }
            .addOnFailureListener { exception ->
                onFailed(exception)
            }
    }

    private fun load_slides(
        documentReference: QuerySnapshot,
        data: ArrayList<AboutSlide>,
        idsSlide: ArrayList<Long>,
        id: Long,
        checked_list_id_number: HashMap<Long, Long>
    ) {
        for (subdocument in documentReference) {
            if (idsSlide.contains(subdocument.getLong("id"))) {
                idsSlide.remove(subdocument.getLong("id"))
                val slide = AboutSlide(
                    subdocument.getLong("id")!!.toLong(),
                    subdocument.getString("image").toString(),
                    subdocument.getString("subtitle").toString(),
                    subdocument.getString("targetLink").toString(),
                    subdocument.getString("text").toString(),
                    subdocument.getString("title").toString(),
                    checked_list_id_number.get(id)!!
                )
                data.add(slide)
            }
        }
    }
}
