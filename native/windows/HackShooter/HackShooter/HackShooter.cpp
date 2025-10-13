#include <iostream>
#include <net_utcode_ui_TestListener.h>
#include "Windows.h"

using namespace std;

JNIEXPORT void JNICALL Java_net_utcode_ui_TestListener_messageBox(JNIEnv*, jobject) {
	MessageBox(NULL, L"‚±‚ê‚ÍC++‚©‚çŒÄ‚Î‚ê‚Ä‚¢‚Ü‚·", L"success", MB_OK | MB_ICONINFORMATION | MB_SETFOREGROUND);
}