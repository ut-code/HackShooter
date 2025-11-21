#include <iostream>
#include <net_utcode_ui_TestListener.h>
#include <net_utcode_webview_WebViewManager.h>
#include "Windows.h"
#include "WebView2.h"
#include <wrl.h>
#include <wrl/client.h>
#include <wil/com.h>
#include "WebView2EnvironmentOptions.h"
#include <Shlwapi.h>

#pragma comment(lib, "Shlwapi.lib")

#define BUTTON_RELOAD 0

using namespace std;
using namespace Microsoft::WRL;


static HANDLE hThread;
static HWND globalHwnd;

// Pointer to WebViewController
static wil::com_ptr<ICoreWebView2Controller> webviewController;

// Pointer to WebView window
static wil::com_ptr<ICoreWebView2> webview;


static TCHAR szWindowClass[] = TEXT("DesktopApp");
static TCHAR szTitle[] = TEXT("WebView sample");

static int dpi;

static HFONT font;
static HWND button;

LRESULT CALLBACK WebViewWndProc(HWND hwnd, UINT msg, WPARAM wp, LPARAM lp) {
	if (!dpi) return DefWindowProc(hwnd, msg, wp, lp);
	switch (msg)
	{
	case WM_SIZE:
		if (webviewController != nullptr) {
			RECT bounds;
			GetClientRect(hwnd, &bounds);
			bounds.top += 30 * dpi / 96;
			bounds.bottom = bounds.top + 630 * dpi / 96;
			bounds.right = bounds.left + 830 * dpi / 96;
			webviewController->put_Bounds(bounds);
		};
		break;
	case WM_DESTROY:
		globalHwnd = NULL;
		hThread = NULL;
		webviewController = nullptr;
		PostQuitMessage(0);
		break;
	case WM_COMMAND:
		switch (LOWORD(wp)) {
		case BUTTON_RELOAD:
			if (webview) {
				webview->Reload();
			}
			if (webviewController) {
				webviewController->MoveFocus(COREWEBVIEW2_MOVE_FOCUS_REASON_PROGRAMMATIC);
			}
			break;
		}
		return 0;
	case WM_PAINT: {
		HDC hdc;
		PAINTSTRUCT ps;
		hdc = BeginPaint(hwnd, &ps);
		if(font) SelectObject(hdc, font);
		RECT rect = { 10 * dpi / 96, 660 * dpi / 96, 1000 * dpi / 96, 700 * dpi / 96};
		DrawText(hdc, TEXT("操作方法　↑↓キー：移動, →←キー：方向転換, スペースキー：発射, シフトキー：必殺技"), -1, &rect, DT_TOP | DT_LEFT);
		EndPaint(hwnd, &ps);
	}
		return DefWindowProc(hwnd, msg, wp, lp);
	case WM_DPICHANGED:
		dpi = HIWORD(wp);
		font = CreateFont(20 * dpi / 96, 0, 0, 0, FW_NORMAL, FALSE, FALSE, 0, SHIFTJIS_CHARSET, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY, VARIABLE_PITCH | FF_SCRIPT, NULL);
		SetWindowPos(button, NULL, 0, 0, 50 * dpi / 96, 30 * dpi / 96, SWP_NOMOVE | SWP_NOZORDER | SWP_NOACTIVATE);
		SendMessage(button, WM_SETFONT, (WPARAM)font, MAKELPARAM(TRUE, 0));
		if (webviewController && dpi) {
			auto controller3 = webviewController.try_query<ICoreWebView2Controller3>();
			if (controller3)
			{
				controller3->put_RasterizationScale((double)96 / dpi);
			}
		}
		return 0;
	default:
		return DefWindowProc(hwnd, msg, wp, lp);
	}

	return 0;
}

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) {

	switch (ul_reason_for_call) {
	case DLL_PROCESS_ATTACH:

		WNDCLASS winc;

		winc.style = CS_HREDRAW | CS_VREDRAW;
		winc.lpfnWndProc = WebViewWndProc;
		winc.cbClsExtra = winc.cbWndExtra = 0;
		winc.hInstance = GetModuleHandle(0);
		winc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
		winc.hCursor = LoadCursor(NULL, IDC_ARROW);
		winc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
		winc.lpszMenuName = NULL;
		winc.lpszClassName = TEXT("WebView2Window");

		RegisterClass(&winc);

		break;
	case DLL_PROCESS_DETACH:
		break;
	case DLL_THREAD_ATTACH:
		break;
	case DLL_THREAD_DETACH:
		break;
	}
	return TRUE;
}

DWORD WINAPI ThreadFunc(LPVOID vdParam) {

	HWND hwnd = CreateWindow(TEXT("WebView2Window"), TEXT("HackShooter(ゲーム部分)"),
		WS_OVERLAPPEDWINDOW,
		0, 0,
		1000, 800,
		NULL, NULL, GetModuleHandle(0), NULL);

	if (hwnd == NULL) return 0;

	globalHwnd = hwnd;

	dpi = GetDpiForWindow(hwnd);

	SetWindowPos(hwnd, NULL, 0, 0, 1000 * dpi / 96, 800 * dpi / 96, SWP_NOMOVE | SWP_NOZORDER | SWP_NOACTIVATE);

	font = CreateFont( 20 * dpi / 96, 0, 0, 0, FW_NORMAL, FALSE, FALSE, 0, SHIFTJIS_CHARSET, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY, VARIABLE_PITCH | FF_SCRIPT, NULL);

	ShowWindow(hwnd, SW_SHOWNORMAL);
	UpdateWindow(hwnd);
	

	HRESULT hr = CreateCoreWebView2EnvironmentWithOptions(nullptr, nullptr, nullptr,
		Callback<ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler>(
			[hwnd](HRESULT result, ICoreWebView2Environment* env) -> HRESULT {

				// Create a CoreWebView2Controller and get the associated CoreWebView2 whose parent is the main window hWnd
				env->CreateCoreWebView2Controller(hwnd, Callback<ICoreWebView2CreateCoreWebView2ControllerCompletedHandler>(
					[hwnd](HRESULT result, ICoreWebView2Controller* controller) -> HRESULT {
						if (controller != nullptr) {
							webviewController = controller;
							webviewController->get_CoreWebView2(&webview);
							webviewController->MoveFocus(COREWEBVIEW2_MOVE_FOCUS_REASON_PROGRAMMATIC);
						}

						// Add a few settings for the webview
						// The demo step is redundant since the values are the default settings
						wil::com_ptr<ICoreWebView2Settings> settings;
						webview->get_Settings(&settings);
						settings->put_IsScriptEnabled(TRUE);
						settings->put_AreDefaultScriptDialogsEnabled(TRUE);
						settings->put_IsWebMessageEnabled(TRUE);

						// Resize WebView to fit the bounds of the parent window
						RECT bounds;
						GetClientRect(hwnd, &bounds);
						bounds.top += 30 * dpi / 96;
						bounds.bottom = bounds.top + 630 * dpi / 96;
						bounds.right = bounds.left + 830 * dpi / 96;
						webviewController->put_Bounds(bounds);

						// Schedule an async task to navigate to Bing
						DWORD len = 1024;
						TCHAR path[1024];
						TCHAR name[1024];

						GetUserName(name, &len);
						StringCchPrintf(path, 1024, L"localhost:80", name);

						webview->Navigate(path);

						return S_OK;
					}).Get());
				return S_OK;
			}).Get());

	button = CreateWindow(
		TEXT("BUTTON"), TEXT("更新"),
		WS_CHILD | WS_VISIBLE | BS_PUSHBUTTON,
		0, 0, 50 * dpi / 96, 30 * dpi / 96,
		hwnd, (HMENU)BUTTON_RELOAD, GetModuleHandle(0), NULL
	);

	SendMessage(button, WM_SETFONT, (WPARAM)font, MAKELPARAM(TRUE, 0));

	MSG msg;

	while (GetMessage(&msg, NULL, 0, 0)) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	return 0;
}

JNIEXPORT void JNICALL Java_net_utcode_webview_WebViewManager_createWindow(JNIEnv*, jclass) {
	if (globalHwnd) SetForegroundWindow(globalHwnd);
	if (webviewController) webviewController->MoveFocus(COREWEBVIEW2_MOVE_FOCUS_REASON_PROGRAMMATIC);
	if (hThread) return;
	
	DWORD dwID;
	hThread = CreateThread(NULL, 0, ThreadFunc, NULL, 0, &dwID);
	return;
}

JNIEXPORT void JNICALL Java_net_utcode_webview_WebViewManager_destroyWindow(JNIEnv*, jclass) {
	PostMessage(globalHwnd, WM_CLOSE, NULL, NULL);
}

JNIEXPORT void JNICALL Java_net_utcode_ui_TestListener_messageBox(JNIEnv*, jobject) {
	MessageBox(NULL, L"これはC++から呼ばれています", L"success", MB_OK | MB_ICONINFORMATION | MB_SETFOREGROUND);
}