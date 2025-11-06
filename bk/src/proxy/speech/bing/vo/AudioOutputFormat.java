package com.qcmz.cmc.proxy.speech.bing.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class AudioOutputFormat {
	/// raw-8khz-8bit-mono-mulaw request output audio format type.
	public static final String Raw8Khz8BitMonoMULaw = "raw-8khz-8bit-mono-mulaw";
	/// raw-16khz-16bit-mono-pcm request output audio format type.
	public static final String Raw16Khz16BitMonoPcm = "raw-16khz-16bit-mono-pcm";
	/// riff-8khz-8bit-mono-mulaw request output audio format type.
	public static final String Riff8Khz8BitMonoMULaw = "riff-16khz-16bit-mono-pcm";
	/// riff-16khz-16bit-mono-pcm request output audio format type.
	public static final String Riff16Khz16BitMonoPcm = "riff-16khz-16bit-mono-pcm";
	/// ssml-16khz-16bit-mono-silk request output audio format type.
	/// It is a SSML with audio segment, with audio compressed by SILK codec
	public static final String Ssml16Khz16BitMonoSilk = "ssml-16khz-16bit-mono-silk";
	/// ssml-16khz-16bit-mono-tts request output audio format type.
	/// It is a SSML with audio segment, and it needs tts engine to play out
	public static final String Ssml16Khz16BitMonoTts = "ssml-16khz-16bit-mono-tts";
}
