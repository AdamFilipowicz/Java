#include <jni.h>
#include <stdio.h>
#include <string.h>
#include "Ladowacz.h"
 
JNIEXPORT void JNICALL Java_Ladowacz_helloWorld(JNIEnv *env, jobject thisObj) {
	printf("Hello World!\n");
	fflush(stdout);
	return;
}

JNIEXPORT jboolean JNICALL Java_Ladowacz_isPrime(JNIEnv *env, jobject thisObj, jint num) {
	if (num <= 1) return 0;
	if (num % 2 == 0 && num > 2) return 0;
	for(int i = 3; i < num / 2; i+= 2){
		if (num % i == 0)
			return 0;
	}
	return 1;
}

JNIEXPORT jfloatArray JNICALL Java_Ladowacz_forEachElement(JNIEnv *env, jobject thisObj, jfloatArray tab, jfloat val, jstring op){
	jsize len = (*env)->GetArrayLength(env, tab);
	jfloat *body = (*env)->GetFloatArrayElements(env, tab, 0);
	const char *str = (*env)->GetStringUTFChars(env, op, 0);

	if(strncmp(str, "add", strlen(str))==0)
		for(int i = 0; i < len; i++)
			body[i] = body[i] + val;
	else if(strncmp(str, "subtract", strlen(str))==0)
		for(int i = 0; i < len; i++)
			body[i] = body[i] - val;
	else if(strncmp(str, "multiply", strlen(str))==0)
		for(int i = 0; i < len; i++)
			body[i] = body[i] * val;
	else if(strncmp(str, "divide", strlen(str))==0)
		for(int i = 0; i < len; i++)
			body[i] = body[i] / val;
	(*env)->ReleaseFloatArrayElements(env, tab, body, 0);
	(*env)->ReleaseStringUTFChars(env, op, str);
	return tab;
}

