package com.example.a7minuteworkout

import java.util.*

/**
 * Constant Class where you can add the constant values of the project.
 */
class Constants {
    companion object {

        // The drawable images used here is added in the drawable folder.
        /**
         * Here we are adding all exercises to a single list with all the default values.
         */
        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks =
                ExerciseModel(1, "Push ups", R.drawable.img01, false, false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Abdominal crunches", R.drawable.img02, false, false)
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(3, "Standing back Stretch", R.drawable.img04, false, false)
            exerciseList.add(pushUp)

            val abdominalCrunch =
                ExerciseModel(4, "Dumbell pushup", R.drawable.img06, false, false)
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair =
                ExerciseModel(
                    5,
                    "dumbell bicep",
                    R.drawable.img07,
                    false,
                    false
                )
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(6, "Standing on one leg", R.drawable.img09, false, false)
            exerciseList.add(squat)

            val tricepDipOnChair =
                ExerciseModel(
                    7,
                    "pushup with ball",
                    R.drawable.img08,
                    false,
                    false
                )
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(8, "front wieght lifting", R.drawable.img10, false, false)
            exerciseList.add(plank)

            val highKneesRunningInPlace =
                ExerciseModel(
                    9, "shoulder on bench",
                    R.drawable.img11,
                    false,
                    false
                )
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(10, "chest press", R.drawable.img12, false, false)
            exerciseList.add(lunges)

            val pushupAndRotation =
                ExerciseModel(
                    11,
                    "high wieght lifting",
                    R.drawable.img13,
                    false,
                    false
                )
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(12, "tilted abdominal crunches", R.drawable.img14, false, false)
            exerciseList.add(sidePlank)

            return exerciseList
        }
    }
}