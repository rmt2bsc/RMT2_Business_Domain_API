package org.modules.audiovideo;

import java.io.File;

import org.apache.log4j.Logger;
import org.dao.mapping.orm.rmt2.AvArtist;
import org.dao.mapping.orm.rmt2.AvProject;
import org.dao.mapping.orm.rmt2.AvTracks;
import org.dto.ArtistDto;
import org.dto.ProjectDto;
import org.dto.TracksDto;
import org.modules.MediaConstants;

import com.RMT2Base;

/**
 * A factory for creating audio video related API objects.
 * 
 * @author Roy Terrell
 * 
 */
public class AudioVideoFactory extends RMT2Base {
    
    public static final Logger LOGGER = Logger.getLogger(AudioVideoFactory.class);
    public static String ERROR_MP3_API = "MP3 Api Instantiation Error: ";

    /**
     * Default contructor
     */
    public AudioVideoFactory() {
        return;
    }

    /**
     * Creates an instance of AudioVideoApi using the
     * {@link AudioVideoMetadataImpl} implementation.
     * 
     * @return an instance of {@link DocumentContentApi}
     */
    public static final AudioVideoApi createApi() {
        return createApi(MediaConstants.APP_NAME);
    }

    /**
     * Creates an instance of AudioVideoApi using the
     * {@link AudioVideoMetadataImpl} implementation.
     */
    public static final AudioVideoApi createApi(String appName) {
        AudioVideoApi api = new AudioVideoMetadataImpl(appName);
        return api;
    }
    
    /**
     * Create and instance of ArtistDto.
     * 
     * @param artist
     *            an instance of {@link ArtistDto}
     * @return an instance of {@link AvArtist}
     */
    public static final AvArtist createArtistInstance(ArtistDto artist) {
        AvArtist a = new AvArtist();
        a.setArtistId(artist.getId());
        a.setName(artist.getName());
        return a;
    }
    
    /**
     * Create an instance of AvTracks by adapting a TracksDto object.
     * 
     * @param track
     *            an instance of {@link TracksDto}
     * @return an instance of {@link AvTracks}
     */
    public static final AvTracks createTrackInstance(TracksDto track) {
        AvTracks t = new AvTracks();
        t.setTrackId(track.getTrackId());
        t.setProjectId(track.getProjectId());
        t.setGenreId(track.getGenreId());
        t.setTrackNumber(track.getTrackNumber());
        t.setTrackTitle(track.getTrackTitle());
        t.setTrackHours(track.getTrackHours());
        t.setTrackMinutes(track.getTrackMinutes());
        t.setTrackSeconds(track.getTrackSeconds());
        t.setTrackDisc(track.getTrackDisc());
        t.setTrackArtist(track.getTrackArtist());
        t.setTrackProducer(track.getTrackProducer());
        t.setTrackComposer(track.getTrackComposer());
        t.setTrackLyricist(track.getTrackLyricist());
        t.setLocFilename(track.getLocFilename());
        t.setLocPath(track.getLocPath());
        t.setLocRootPath(track.getLocRootPath());
        t.setLocServername(track.getLocServername());
        t.setLocSharename(track.getLocSharename());
        t.setComments(track.getComments());
        t.setDateCreated(track.getDateCreated());
        return t;
    
    }

    /**
     * Create an instance of AvProject by adapting a ProjectDto object.
     * 
     * @param proj
     *            an instance of {@link ProjectDto}
     * @return an instance of {@link AvProject}
     */
    public static final AvProject createProjectInstance(ProjectDto proj) {
        AvProject p = new AvProject();
        p.setProjectId(proj.getProjectId());
        p.setArtistId(proj.getArtistId());
        p.setProjectTypeId(proj.getProjectTypeId());
        p.setGenreId(proj.getGenreId());
        p.setMediaTypeId(proj.getMediaTypeId());
        p.setTitle(proj.getTitle());
        p.setYear(proj.getYear());
        p.setTotalTime(proj.getTotalTime());
        p.setMasterDupId(proj.getMasterDupId());
        p.setRipped(proj.getRippedInd());
        p.setCost(proj.getCost());
        p.setProjectComments(proj.getComments());
        p.setContentId(proj.getContentId());
        p.setDateCreated(proj.getDateCreated());
        p.setContentPath(proj.getContentPath());
        p.setContentFilename(proj.getContentFilename());
        p.setArtWorkFilename(proj.getArtWorkFilename());
        p.setArtWorkPath(proj.getArtWorkPath());
        p.setProducer(proj.getProducer());
        return p;
    }

    /**
     * Create an instance of MP3Reader from a Mp3agic API implementation.
     * 
     * @param mp3Source
     * @return an instacne of {@link MP3Reader}
     * @throws MP3ApiInstantiationException
     */
    public static MP3Reader createMp3agicInstance(File mp3Source) {
        String msg = AudioVideoFactory.ERROR_MP3_API;
        MP3Reader api = null;
        try {
            api = new Mp3agicMp3LibImpl(mp3Source);
            AudioVideoFactory.LOGGER.info("API is using Id3Mp3Wmv implementation for MP3Reader");
            return api;
        } catch (Throwable e) {
            msg += "Unable to create Id3Mp3Wmv MP3Reader implementation for media resource, " + mp3Source.getAbsolutePath();
            throw new MP3ApiInstantiationException(msg, e);
        }
    }
}
