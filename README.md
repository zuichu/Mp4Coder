# Mp4Coder
Mp4Coder，H264和AAC合成MP4视频库


###2016.10.26更新库：    
  
支持流媒体AAC音频合成

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

也可以根据实际情况自定义帧率：  
   
```
 H264TrackImpl h264Track1 = new H264TrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/video.h264"), "eng", 15000, 1001);
 
给出源码解释：

 /**
     * Creates a new <code>Track</code> object from a raw H264 source (<code>DataSource dataSource1</code>).
     * Whenever the timescale and frametick are set to negative value (e.g. -1) the H264TrackImpl
     * tries to detect the frame rate.
     * Typically values for <code>timescale</code> and <code>frametick</code> are:
     * <ul>
     * <li>23.976 FPS: timescale = 24000; frametick = 1001</li>
     * <li>25 FPS: timescale = 25; frametick = 1</li>
     * <li>29.97 FPS: timescale = 30000; frametick = 1001</li>
     * <li>30 FPS: timescale = 30; frametick = 1</li>
     * </ul>
     *
     * @param dataSource the source file of the H264 samples
     * @param lang       language of the movie (in doubt: use "eng")
     * @param timescale  number of time units (ticks) in one second
     * @param frametick  number of time units (ticks) that pass while showing exactly one frame
     * @throws IOException in case of problems whiel reading from the <code>DataSource</code>
     */
    public H264TrackImpl(DataSource dataSource, String lang, long timescale, int frametick) throws IOException {
        super(dataSource);
        this.lang = lang;
        this.timescale = timescale; //e.g. 23976
        this.frametick = frametick;
        if ((timescale > 0) && (frametick > 0)) {
            this.determineFrameRate = false;
        }

        parse(new LookAhead(dataSource));
    }
```
   
   
可以实现H264编码视频文件和AAC音频文件合成为MP4视频文件，也可以实现多个mp4文件拼接。  

```
//拼接示例  

 List<String> fileList = new ArrayList<String>();
        List<Movie> moviesList = new LinkedList<Movie>();
        fileList.add(Environment.getExternalStorageDirectory() + "/1.mp4");
        fileList.add(Environment.getExternalStorageDirectory() + "/2.mp4");
        try {
            for (String file : fileList) {
                moviesList.add(MovieCreator.build(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();
        for (Movie m : moviesList) {
            for (Track t : m.getTracks()) {
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }

        Movie result = new Movie();

        try {
            if (audioTracks.size() > 0) {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Container out = new DefaultMp4Builder().build(result);

        try {
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/encode.mp4")).getChannel();
            out.writeContainer(fc);
            fc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        moviesList.clear();
        fileList.clear();

```

可使用aar库，方便些。  
此为Android库  
