# Mp4Coder
Mp4Coder，H264和AAC合成MP4视频库

用法


```
        try {
            H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/imgBufFrame.h264"));
            AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/test.aac"));
            Movie movie = new Movie();
            movie.addTrack(h264Track);
            movie.addTrack(aacTrack);
            Container mp4file = new DefaultMp4Builder().build(movie);
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/encode.mp4")).getChannel();
            mp4file.writeContainer(fc);
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

```

可以实现H264编码视频文件和AAC音频文件合成为MP4视频文件，也可以实现多个mp4文件拼接
