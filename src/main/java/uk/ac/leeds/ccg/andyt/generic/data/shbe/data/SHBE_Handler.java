/*
 * Copyright (C) 2014 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.data.shbe.data;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.core.ONSPD_ID;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Point;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
//import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Time;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.util.ONSPD_YM3;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.util.SHBE_Collections;
//import uk.ac.leeds.ccg.andyt.math.Generic_BigDecimal;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.data.underoccupied.DW_UO_Record;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.data.underoccupied.DW_UO_Set;

/**
 * Class for handling SHBE Data.
 *
 * @author geoagdt
 */
public class SHBE_Handler extends SHBE_Object {

    /**
     * For convenience, these are initialised in construction from Env.
     */
    //private final transient HashMap<String, SHBE_ID> NINOToNINOIDLookup;
    //private final transient HashMap<String, SHBE_ID> DOBToDOBIDLookup;
    private final transient ONSPD_Handler ONSPD_Handler;

    /**
     * For a set of expected RecordTypes. ("A", "D", "C", "R", "T", "P", "G",
     * "E", "S").
     */
    protected HashSet<String> RecordTypes;

    /**
     * The main data.
     */
    protected HashMap<ONSPD_YM3, SHBE_Records> Data;

    /**
     * File for storing Data
     */
    private File DataFile;

    /**
     * ClaimRef to ClaimID Lookup.
     */
    private HashMap<String, SHBE_ID> ClaimRefToClaimIDLookup;

    /**
     * ClaimID to ClaimRef Lookup.
     */
    private HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup;

    private SHBE_CorrectedPostcodes CorrectedPostcodes;

    /**
     * National Insurance Number to NINO SHBE_ID Lookup.
     */
    private HashMap<String, SHBE_ID> NINOToNINOIDLookup;

    /**
     * NINO SHBE_ID to National Insurance Number Lookup.
     */
    private HashMap<SHBE_ID, String> NINOIDToNINOLookup;

    /**
     * Date Of Birth to DoB SHBE_ID Lookup.
     */
    private HashMap<String, SHBE_ID> DOBToDOBIDLookup;

    /**
     * DoB SHBE_ID to Date Of Birth Lookup.
     */
    private HashMap<SHBE_ID, String> DOBIDToDOBLookup;

    /**
     * SHBE_PersonID of Claimants
     */
    HashSet<SHBE_PersonID> ClaimantPersonIDs;

    /**
     * SHBE_PersonID of Partners
     */
    HashSet<SHBE_PersonID> PartnerPersonIDs;

    /**
     * SHBE_PersonID of Non-Dependents
     */
    HashSet<SHBE_PersonID> NonDependentPersonIDs;

    /**
     * SHBE_PersonID to ClaimIDs Lookup
     */
    HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDsLookup;

    /**
     * Postcode to Postcode SHBE_ID Lookup.
     */
    private HashMap<String, ONSPD_ID> PostcodeToPostcodeIDLookup;

    /**
     * Postcode SHBE_ID to Postcode Lookup.
     */
    private HashMap<ONSPD_ID, String> PostcodeIDToPostcodeLookup;

    /**
     * Postcode SHBE_ID to ONSPD_Point Lookups. There is a different one for
     * each ONSPD File. The keys are Nearest YM3s for the respective ONSPD File.
     */
    private HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> PostcodeIDToPointLookups;

    /**
     * ClaimRefToClaimIDLookup File.
     */
    private File ClaimRefToClaimIDLookupFile;

    /**
     * ClaimIDToClaimRefLookupFile File.
     */
    private File ClaimIDToClaimRefLookupFile;

    /**
     * NINOIDToNINOLookup File.
     */
    private File NINOIDToNINOLookupFile;

    /**
     * IDToDOBLookup File.
     */
    private File DOBIDToDOBLookupFile;

    /**
     * ClaimantPersonIDs File.
     */
    private File ClaimantPersonIDsFile;

    /**
     * PartnerPersonIDs File.
     */
    private File PartnerPersonIDsFile;

    /**
     * NonDependentPersonIDs File.
     */
    private File NonDependentPersonIDsFile;

    /**
     * PersonIDToClaimIDsLookup File.
     */
    private File PersonIDToClaimIDsLookupFile;

    /**
     * CorrectedPostcodes File.
     */
    private File CorrectedPostcodesFile;

    /**
     * NINOToIDLookup File.
     */
    private File NINOToNINOIDLookupFile;

    /**
     * DOBToSHBE_IDLookup File.
     */
    private File DOBToDOBIDLookupFile;

    /**
     * PostcodeToPostcodeIDLookup File.
     */
    private File PostcodeToPostcodeIDLookupFile;

    /**
     * PostcodeIDToPostcodeLookup File.
     */
    private File PostcodeIDToPostcodeLookupFile;

    /**
     * PostcodeIDToPointLookup File.
     */
    private File PostcodeIDToPointLookupsFile;

    public SHBE_Handler(SHBE_Environment e) {
        this(e, e.ge.initLog("SHBE_Handler"));
    }

    public SHBE_Handler(SHBE_Environment e, int logID) {
        super(e, logID);
//        NINOToNINOIDLookup = e.getNINOToNINOIDLookup();
//        DOBToDOBIDLookup = Data.getDOBToDOBIDLookup();
        ONSPD_Handler = e.oe.getHandler();
    }

    /**
     * For loading in all SHBE Data.
     *
     * @param logID The ID of the log for writing to.
     */
    public void run(int logID) {
        String[] SHBEFilenames;
        SHBEFilenames = getSHBEFilenamesAll();
        ONSPD_YM3 LastYM3;
        LastYM3 = getYM3(SHBEFilenames[SHBEFilenames.length - 1]);
        ONSPD_YM3 nYM3;
        nYM3 = ONSPD_Handler.getNearestYM3ForONSPDLookup(LastYM3);
        File dir;
        dir = files.getInputSHBEDir();
        for (String SHBEFilename : SHBEFilenames) {
            SHBE_Records recs;
            recs = new SHBE_Records(Env, logID, dir, SHBEFilename, nYM3);
            Generic_IO.writeObject(recs, recs.getFile(), recs.toString());
        }
        writeLookups();
        // Make a backup copy
        File SHBEbackup;
        SHBEbackup = new File(files.getGeneratedLCCDir(), "SHBEBackup");
        if (SHBEbackup.isDirectory()) {
            SHBEbackup = Generic_IO.addToArchive(SHBEbackup, 100);
        } else {
            SHBEbackup = Generic_IO.initialiseArchive(SHBEbackup, 100);
        }
        Generic_IO.copy(files.getGeneratedSHBEDir(), SHBEbackup);
    }

    /**
     * {@code
     * if (Data == null) {
     * if (f.exists()) {
     * Data = (HashMap<String, SHBE_Records>) Generic_IO.readObject(f);
     * } else {
     * Data = new HashMap<String, SHBE_Records>(); } } return Data; }
     *
     * @param f
     * @return
     */
    public HashMap<ONSPD_YM3, SHBE_Records> getData(File f) {
        if (Data == null) {
            if (f.exists()) {
                Data = (HashMap<ONSPD_YM3, SHBE_Records>) Generic_IO.readObject(f);
            } else {
                Data = new HashMap<>();
            }
        }
        return Data;
    }

    /**
     * {@code
     * DataFile = getDataFile();
     * return getData(DataFile);
     * }
     *
     * @return
     */
    public HashMap<ONSPD_YM3, SHBE_Records> getData() {
        DataFile = getDataFile();
        return getData(DataFile);
    }

    /**
     * If DataFile is null, initialise it.
     *
     * @return DataFile
     */
    public final File getDataFile() {
        if (DataFile == null) {
            String filename = "Data_HashMap_String__SHBE_Records"
                    + SHBE_Strings.s_BinaryFileExtension;
            DataFile = new File(files.getGeneratedSHBEDir(), filename);
        }
        return DataFile;
    }

    /**
     * If ClaimRefToClaimIDLookup is null initialise it.
     *
     * @param f
     * @return ClaimRefToClaimIDLookup
     */
    public HashMap<String, SHBE_ID> getClaimRefToClaimIDLookup(File f) {
        if (ClaimRefToClaimIDLookup == null) {
            ClaimRefToClaimIDLookup = getStringToIDLookup(f);
        }
        return ClaimRefToClaimIDLookup;
    }

    /**
     * {@code HashMap<String, SHBE_ID> result;
     * if (f.exists()) {
     * result = (HashMap<String, SHBE_ID>) Generic_IO.readObject(f);
     * } else {
     * result = new HashMap<String, SHBE_ID>(); } return result;}
     *
     * @param f
     * @return
     */
    public HashMap<String, SHBE_ID> getStringToIDLookup(File f) {
        HashMap<String, SHBE_ID> r;
        if (f.exists()) {
            r = (HashMap<String, SHBE_ID>) Generic_IO.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    /**
     * If ClaimIDToClaimRefLookup is null initialise it.
     *
     * @param f
     * @return ClaimIDToClaimRefLookup
     */
    public HashMap<SHBE_ID, String> getClaimIDToClaimRefLookup(File f) {
        if (ClaimIDToClaimRefLookup == null) {
            ClaimIDToClaimRefLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return ClaimIDToClaimRefLookup;
    }

    /**
     * {@code ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
     * return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getClaimRefToClaimIDLookup() {
        ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
        return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);
    }

    /**
     * {@code ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
     * return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getClaimIDToClaimRefLookup() {
        ClaimIDToClaimRefLookupFile = getClaimIDToClaimRefLookupFile();
        return getClaimIDToClaimRefLookup(ClaimIDToClaimRefLookupFile);
    }

    public final SHBE_CorrectedPostcodes getCorrectedPostcodes(
            File f) {
        if (CorrectedPostcodes == null) {
            if (f.exists()) {
                CorrectedPostcodes = (SHBE_CorrectedPostcodes) Generic_IO.readObject(f);
            } else {
                new SHBE_CorrectedPostcodes(Env).run();
                return getCorrectedPostcodes(f);
            }
        }
        return CorrectedPostcodes;
    }

    public SHBE_CorrectedPostcodes getCorrectedPostcodes() {
        CorrectedPostcodesFile = getCorrectedPostcodesFile();
        return getCorrectedPostcodes(CorrectedPostcodesFile);
    }

    /**
     * {@code if (NINOToSHBE_IDLookup == null) {
     * NINOToSHBE_IDLookup = getStringToIDLookup(f);
     * }
     * return NINOToSHBE_IDLookup;}
     *
     * @param f
     * @return NINOToSHBE_IDLookup
     */
    public final HashMap<String, SHBE_ID> getNINOToNINOIDLookup(
            File f) {
        if (NINOToNINOIDLookup == null) {
            NINOToNINOIDLookup = getStringToIDLookup(f);
        }
        return NINOToNINOIDLookup;
    }

    /**
     * {@code NINOToSHBE_IDLookupFile = getNINOToIDLookupFile();
     * return getNINOToNINOIDLookup(NINOToSHBE_IDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getNINOToNINOIDLookup() {
        NINOToNINOIDLookupFile = getNINOToNINOIDLookupFile();
        return getNINOToNINOIDLookup(NINOToNINOIDLookupFile);
    }

    /**
     * {@code if (DOBToDOBIDLookup == null) {
     * DOBToDOBIDLookup = getStringToIDLookup(f);
     * }
     * return DOBToDOBIDLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<String, SHBE_ID> getDOBToDOBIDLookup(
            File f) {
        if (DOBToDOBIDLookup == null) {
            DOBToDOBIDLookup = getStringToIDLookup(f);
        }
        return DOBToDOBIDLookup;
    }

    /**
     * {@code DOBToDOBIDLookupFile = getDOBToDOBIDLookupFile();
     * return getDOBToDOBIDLookup(DOBToDOBIDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getDOBToDOBIDLookup() {
        DOBToDOBIDLookupFile = getDOBToDOBIDLookupFile();
        return getDOBToDOBIDLookup(DOBToDOBIDLookupFile);
    }

    /**
     * {@code if (NINOIDToNINOLookup == null) {
     * NINOIDToNINOLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return NINOIDToNINOLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<SHBE_ID, String> getNINOIDToNINOLookup(
            File f) {
        if (NINOIDToNINOLookup == null) {
            NINOIDToNINOLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return NINOIDToNINOLookup;
    }

    /**
     * {@code NINOIDToNINOLookupFile = getNINOIDToNINOLookupFile();
     * return getNINOIDToNINOLookup(NINOIDToNINOLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getNINOIDToNINOLookup() {
        NINOIDToNINOLookupFile = getNINOIDToNINOLookupFile();
        return getNINOIDToNINOLookup(NINOIDToNINOLookupFile);
    }

    /**
     * {@code if (DOBIDToDOBLookup == null) {
     * DOBIDToDOBLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return DOBIDToDOBLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<SHBE_ID, String> getDOBIDToDOBLookup(
            File f) {
        if (DOBIDToDOBLookup == null) {
            DOBIDToDOBLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return DOBIDToDOBLookup;
    }

    /**
     * {@code DOBIDToDOBLookupFile = getDOBIDToDOBLookupFile();
     * return getDOBIDToDOBLookup(DOBIDToDOBLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getDOBIDToDOBLookup() {
        DOBIDToDOBLookupFile = getDOBIDToDOBLookupFile();
        return getDOBIDToDOBLookup(DOBIDToDOBLookupFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getClaimantPersonIDs(
            File f) {
        if (ClaimantPersonIDs == null) {
            ClaimantPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return ClaimantPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getClaimantPersonIDs() {
        ClaimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(ClaimantPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getPartnerPersonIDs(
            File f) {
        if (PartnerPersonIDs == null) {
            PartnerPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return PartnerPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getPartnerPersonIDs() {
        PartnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(PartnerPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getNonDependentPersonIDs(
            File f) {
        if (NonDependentPersonIDs == null) {
            NonDependentPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return NonDependentPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getNonDependentPersonIDs() {
        NonDependentPersonIDsFile = getNonDependentPersonIDsFile();
        return getNonDependentPersonIDs(NonDependentPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDsLookup(
            File f) {
        if (PersonIDToClaimIDsLookup == null) {
            PersonIDToClaimIDsLookup = SHBE_Collections.getHashMap_SHBE_PersonID__HashSet_SHBE_ID(f);
        }
        return PersonIDToClaimIDsLookup;
    }

    /**
     * All SHBE_PersonID to ClaimIDs Lookup
     *
     * @return
     */
    public HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDLookup() {
        PersonIDToClaimIDsLookupFile = getPersonIDToClaimIDLookupFile();
        return getPersonIDToClaimIDsLookup(PersonIDToClaimIDsLookupFile);
    }

    /**
     * All SHBE_PersonID to ClaimIDs Lookup
     */
    HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDLookup;

    /**
     * {@code if (PostcodeToPostcodeIDLookup == null) {
     * PostcodeToPostcodeIDLookup = getStringToIDLookup(f);
     * }
     * return PostcodeToPostcodeIDLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<String, ONSPD_ID> getPostcodeToPostcodeIDLookup(
            File f) {
        if (PostcodeToPostcodeIDLookup == null) {
            if (f.exists()) {
                PostcodeToPostcodeIDLookup = (HashMap<String, ONSPD_ID>) Generic_IO.readObject(f);
            } else {
                PostcodeToPostcodeIDLookup = new HashMap<>();
            }
        }
        return PostcodeToPostcodeIDLookup;
    }

    /**
     * {@code PostcodeToPostcodeDWLookupFile = getPostcodeToPostcodeIDLookupFile();
     * return getPostcodeToPostcodeIDLookup(PostcodeToPostcodeDWLookupFile);}
     *
     * @return
     */
    public HashMap<String, ONSPD_ID> getPostcodeToPostcodeIDLookup() {
        PostcodeToPostcodeIDLookupFile = getPostcodeToPostcodeIDLookupFile();
        return getPostcodeToPostcodeIDLookup(PostcodeToPostcodeIDLookupFile);
    }

    /**
     * {@code if (PostcodeIDToPostcodeLookup == null) {
     * PostcodeIDToPostcodeLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return PostcodeIDToPostcodeLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<ONSPD_ID, String> getPostcodeIDToPostcodeLookup(
            File f) {
        if (PostcodeIDToPostcodeLookup == null) {
            if (f.exists()) {
                PostcodeIDToPostcodeLookup = (HashMap<ONSPD_ID, String>) Generic_IO.readObject(f);
            } else {
                PostcodeIDToPostcodeLookup = new HashMap<>();
            }
        }
        return PostcodeIDToPostcodeLookup;
    }

    /**
     * {@code if (PostcodeIDToPointLookups == null) {
     * if (f.exists()) {
     * PostcodeIDToPointLookups = (HashMap<String, HashMap<SHBE_ID, ONSPD_Point>>) Generic_IO.readObject(f);
     * } else {
     * PostcodeIDToPointLookups = new HashMap<String, HashMap<SHBE_ID, ONSPD_Point>>();
     * } } return PostcodeIDToPointLookups;}
     *
     * @param f
     * @return
     */
    public final HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> getPostcodeIDToPointLookups(
            File f) {
        if (PostcodeIDToPointLookups == null) {
            if (f.exists()) {
                PostcodeIDToPointLookups = (HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>>) Generic_IO.readObject(f);
            } else {
                PostcodeIDToPointLookups = new HashMap<>();
            }
        }
        return PostcodeIDToPointLookups;
    }

    /**
     * {@code PostcodeIDToPostcodeLookupFile = getPostcodeIDToPostcodeLookupFile();
     * return getPostcodeIDToPostcodeLookup(PostcodeIDToPostcodeLookupFile);}
     *
     * @return
     */
    public HashMap<ONSPD_ID, String> getPostcodeIDToPostcodeLookup() {
        PostcodeIDToPostcodeLookupFile = getPostcodeIDToPostcodeLookupFile();
        return getPostcodeIDToPostcodeLookup(PostcodeIDToPostcodeLookupFile);
    }

    /**
     * {@code PostcodeIDToAGDT_PointLookupFile = getPostcodeIDToAGDT_PointLookupsFile();
     * return getPostcodeIDToPointLookups(PostcodeIDToAGDT_PointLookupFile);}
     *
     * @return
     */
    public HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> getPostcodeIDToPointLookups() {
        PostcodeIDToPointLookupsFile = getPostcodeIDToPointLookupsFile();
        return getPostcodeIDToPointLookups(PostcodeIDToPointLookupsFile);
    }

    /**
     * {@code PostcodeIDToAGDT_PointLookupFile = getPostcodeIDToAGDT_PointLookupsFile();
     * return getPostcodeIDToPointLookups(PostcodeIDToAGDT_PointLookupFile);}
     *
     * @param YM3
     * @return
     */
    public HashMap<ONSPD_ID, ONSPD_Point> getPostcodeIDToPointLookup(ONSPD_YM3 YM3) {
        ONSPD_YM3 NearestYM3ForONSPDLookup;
        NearestYM3ForONSPDLookup = ONSPD_Handler.getNearestYM3ForONSPDLookup(YM3);
        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup;
        PostcodeIDToPointLookups = getPostcodeIDToPointLookups();
        if (PostcodeIDToPointLookups.containsKey(NearestYM3ForONSPDLookup)) {
            PostcodeIDToPointLookup = PostcodeIDToPointLookups.get(NearestYM3ForONSPDLookup);
        } else {
            PostcodeIDToPointLookup = new HashMap<>();
            PostcodeIDToPointLookups.put(NearestYM3ForONSPDLookup, PostcodeIDToPointLookup);
        }
        return PostcodeIDToPointLookup;
    }

    /**
     * {@code if (ClaimRefToClaimIDLookupFile == null) {
     * String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.s_BinaryFileExtension;
     * PostcodeToPostcodeIDLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return ClaimRefToClaimIDLookupFile;}
     *
     * @return
     */
    public final File getClaimRefToClaimIDLookupFile() {
        if (ClaimRefToClaimIDLookupFile == null) {
            String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            ClaimRefToClaimIDLookupFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return ClaimRefToClaimIDLookupFile;
    }

    /**
     * {@code if (ClaimIDToClaimRefLookupFile == null) {
     * String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
     * + SHBE_Strings.s_BinaryFileExtension;
     * ClaimIDToClaimRefLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return ClaimIDToClaimRefLookupFile;}
     *
     * @return
     */
    public final File getClaimIDToClaimRefLookupFile() {
        if (ClaimIDToClaimRefLookupFile == null) {
            String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            ClaimIDToClaimRefLookupFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return ClaimIDToClaimRefLookupFile;
    }

    /**
     * {@code if (PostcodeToPostcodeDWLookupFile == null) {
     * String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
     * + strings.class;
     * PostcodeToPostcodeDWLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeToPostcodeDWLookupFile;}
     *
     * @return
     */
    public final File getPostcodeToPostcodeIDLookupFile() {
        if (PostcodeToPostcodeIDLookupFile == null) {
            String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            PostcodeToPostcodeIDLookupFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeToPostcodeIDLookupFile;
    }

    /**
     * {@code if (PostcodeIDToPostcodeLookupFile == null) {
     * String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
     * + SHBE_Strings.s_BinaryFileExtension;
     * PostcodeIDToPostcodeLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeIDToPostcodeLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPostcodeLookupFile() {
        if (PostcodeIDToPostcodeLookupFile == null) {
            String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            PostcodeIDToPostcodeLookupFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeIDToPostcodeLookupFile;
    }

    /**
     * {@code if (PostcodeIDToAGDT_PointLookupFile == null) {
     * String filename = "PostcodeIDToAGDT_Point_HashMap_SHBE_ID__AGDT_Point"
     * + SHBE_Strings.s_BinaryFileExtension;
     * PostcodeIDToAGDT_PointLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeIDToAGDT_PointLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPointLookupsFile() {
        if (PostcodeIDToPointLookupsFile == null) {
            String filename = "PostcodeIDToPoint_HashMap_String__HashMap_SHBE_ID__AGDT_Point"
                    + SHBE_Strings.s_BinaryFileExtension;
            PostcodeIDToPointLookupsFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeIDToPointLookupsFile;
    }

    public final File getCorrectedPostcodesFile() {
        if (CorrectedPostcodesFile == null) {
            String filename = "DW_CorrectedPostcodes"
                    + SHBE_Strings.s_BinaryFileExtension;
            CorrectedPostcodesFile = new File(files.getGeneratedLCCDir(),
                    filename);
        }
        return CorrectedPostcodesFile;
    }

    /**
     * {@code if (NINOToNINOIDLookupFile == null) {
     * String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.s_BinaryFileExtension;
     * NINOToNINOIDLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return NINOToNINOIDLookupFile;}
     *
     * @return
     */
    public final File getNINOToNINOIDLookupFile() {
        if (NINOToNINOIDLookupFile == null) {
            String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            NINOToNINOIDLookupFile = new File(
                    files.getGeneratedSHBEDir(), filename);
        }
        return NINOToNINOIDLookupFile;
    }

    /**
     * {@code if (DOBToSHBE_IDLookupFile == null) {
     * String filename = "DOBToID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.s_BinaryFileExtension;
     * DOBToSHBE_IDLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return DOBToSHBE_IDLookupFile;}
     *
     * @return
     */
    public final File getDOBToDOBIDLookupFile() {
        if (DOBToDOBIDLookupFile == null) {
            String filename = "DOBToDOBID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            DOBToDOBIDLookupFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return DOBToDOBIDLookupFile;
    }

    /**
     * {@code if (NINOIDToNINOLookupFile == null) {
     * String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
     * + SHBE_Strings.s_BinaryFileExtension;
     * NINOIDToNINOLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return NINOIDToNINOLookupFile;}
     *
     * @return
     */
    public final File getNINOIDToNINOLookupFile() {
        if (NINOIDToNINOLookupFile == null) {
            String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            NINOIDToNINOLookupFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return NINOIDToNINOLookupFile;
    }

    /**
     * {@code if (DOBIDToDOBLookupFile == null) {
     * String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
     * + SHBE_Strings.s_BinaryFileExtension;
     * DOBIDToDOBLookupFile = new File(
     * files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return DOBIDToDOBLookupFile;}
     *
     * @return
     */
    public final File getDOBIDToDOBLookupFile() {
        if (DOBIDToDOBLookupFile == null) {
            String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            DOBIDToDOBLookupFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return DOBIDToDOBLookupFile;
    }

    public final File getClaimantPersonIDsFile() {
        if (ClaimantPersonIDsFile == null) {
            String filename = "Claimant_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            ClaimantPersonIDsFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return ClaimantPersonIDsFile;
    }

    public final File getPartnerPersonIDsFile() {
        if (PartnerPersonIDsFile == null) {
            String filename = "Partner_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            PartnerPersonIDsFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return PartnerPersonIDsFile;
    }

    public final File getNonDependentPersonIDsFile() {
        if (NonDependentPersonIDsFile == null) {
            String filename = "NonDependent_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            NonDependentPersonIDsFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return NonDependentPersonIDsFile;
    }

    public final File getPersonIDToClaimIDLookupFile() {
        if (PersonIDToClaimIDsLookupFile == null) {
            String filename = "PersonIDToClaimIDsLookup_"
                    + "HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            PersonIDToClaimIDsLookupFile = new File(files.getGeneratedSHBEDir(),
                    filename);
        }
        return PersonIDToClaimIDsLookupFile;
    }

    /**
     * If getData().get(YM3) != null then return it.Otherwise try to load it
     * from file and return it. Failing that return null.
     *
     * @param ym3
     * @param hoome
     * @return
     */
    public SHBE_Records getRecords(ONSPD_YM3 ym3, boolean hoome) {
        try {
            return getRecords(ym3);
        } catch (OutOfMemoryError e) {
            if (hoome) {
                Env.clearMemoryReserve();
                Env.clearAllData();
                SHBE_Records r = getRecords(ym3, hoome);
                Env.initMemoryReserve();
                return r;
            } else {
                throw e;
            }
        }
    }

    /**
     * If getData().get(YM3) != null then return it. Otherwise try to load it
     * from file and return it. Failing that return null.
     *
     * @param ym3
     * @return
     */
    protected SHBE_Records getRecords(ONSPD_YM3 ym3) {
        SHBE_Records r;
        r = getData().get(ym3);
        if (r == null) {
            File f;
            f = getFile(ym3);
            if (f.exists()) {
                r = (SHBE_Records) Generic_IO.readObject(f);
                r.Env = Env;
                return r;
            }
        }
        return r;
    }

    /**
     * {@code return new File(files.getGeneratedSHBEDir(), YM3.toString());}
     *
     * @param YM3
     * @return
     */
    protected File getDir(ONSPD_YM3 YM3) {
        return new File(files.getGeneratedSHBEDir(), YM3.toString());
    }

    /**
     *
     * @param ym3
     * @return
     */
    protected File getFile(ONSPD_YM3 ym3) {
        File r;
        File dir;
        dir = getDir(ym3);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        r = new File(dir, SHBE_Strings.s_Records + SHBE_Strings.s_BinaryFileExtension);
        //r = new File(dir, SHBE_Strings.s_SHBE + "_" + SHBE_Strings.s_Records + SHBE_Strings.s_BinaryFileExtension);
        return r;
    }

    /**
     * Clears all SHBE_Records in Data from fast access memory.
     *
     * @return The number of SHBE_Records cleared.
     */
    public int clearAll() {
        String methodName = "clearAll";
        Env.ge.log("<" + methodName + ">");
        int r = 0;
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 YM3;
        SHBE_Records recs;
        while (ite.hasNext()) {
            YM3 = ite.next();
            recs = Data.get(YM3);
            if (recs != null) {
                recs = null;
                r++;
            }
        }
        Env.ge.log("</" + methodName + ">");
        return r;
    }

    /**
     * Clears all SHBE_Records in Data from fast access memory with the
     * exception of that for YM3.
     *
     * @param ym3 The Year_Month key of the SHBE_Records not to be cleared from
     * fast access memory.
     * @return The number of SHBE_Records cleared.
     */
    public int clearAllExcept(ONSPD_YM3 ym3) {
        int r = 0;
        Iterator<ONSPD_YM3> ite = Data.keySet().iterator();
        while (ite.hasNext()) {
            ONSPD_YM3 y = ite.next();
            if (!y.equals(ym3)) {
                SHBE_Records recs = Data.get(ym3);
                if (recs != null) {
                    recs = null;
                    r++;
                }
            }
        }
        return r;
    }

    /**
     * Clears some SHBE_Records in Data from fast access memory.
     *
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSome() {
        Iterator<ONSPD_YM3> ite = Data.keySet().iterator();
        while (ite.hasNext()) {
            ONSPD_YM3 y = ite.next();
            SHBE_Records recs = Data.get(y);
            if (recs != null) {
                recs = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Clears some SHBE_Records in Data from fast access memory except the
     * SHBE_Records in Data indexed by YM3.
     *
     * @param ym3
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeExcept(ONSPD_YM3 ym3) {
        Iterator<ONSPD_YM3> ite = Data.keySet().iterator();
        while (ite.hasNext()) {
            ONSPD_YM3 y = ite.next();
            if (!ym3.equals(y)) {
                SHBE_Records recs = Data.get(ym3);
                if (recs != null) {
                    recs = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears SHBE_Records for YM3 in Data from fast access memory.
     *
     * @param ym3 The Year_Month key for accessing the SHBE_Records to be
     * cleared from fast access memory.
     * @return true iff the data were cleared and false otherwise (when the data
     * is already cleared).
     */
    public boolean clear(ONSPD_YM3 ym3) {
        SHBE_Records recs = Data.get(ym3);
        if (recs != null) {
            recs = null;
            return true;
        }
        return false;
    }

    public void writeLookups() {
        Generic_IO.writeObject(getClaimIDToClaimRefLookup(),
                getClaimIDToClaimRefLookupFile());
        Generic_IO.writeObject(getClaimRefToClaimIDLookup(),
                getClaimRefToClaimIDLookupFile());
        Generic_IO.writeObject(getNINOToNINOIDLookup(),
                getNINOToNINOIDLookupFile());
        Generic_IO.writeObject(getNINOIDToNINOLookup(),
                getNINOIDToNINOLookupFile());
        Generic_IO.writeObject(getDOBToDOBIDLookup(),
                getDOBToDOBIDLookupFile());
        Generic_IO.writeObject(getDOBIDToDOBLookup(),
                getDOBIDToDOBLookupFile());
        Generic_IO.writeObject(getPostcodeToPostcodeIDLookup(),
                getPostcodeToPostcodeIDLookupFile());
        Generic_IO.writeObject(getPostcodeIDToPostcodeLookup(),
                getPostcodeIDToPostcodeLookupFile());
        Generic_IO.writeObject(getPostcodeIDToPointLookups(),
                getPostcodeIDToPointLookupsFile());
        Generic_IO.writeObject(getClaimantPersonIDs(),
                getClaimantPersonIDsFile());
        Generic_IO.writeObject(getPartnerPersonIDs(),
                getPartnerPersonIDsFile());
        Generic_IO.writeObject(getNonDependentPersonIDs(),
                getNonDependentPersonIDsFile());
        Generic_IO.writeObject(getPersonIDToClaimIDLookup(),
                getPersonIDToClaimIDLookupFile());
    }

    /**
     * For loading in new SHBE data
     *
     */
    public void runNew() {
        File dir;
        dir = Env.files.getInputSHBEDir();
        // Ascertain which files are new and need loading
        String[] SHBEFilenames = getSHBEFilenamesAll();
        ArrayList<String> newFilesToRead = new ArrayList<>();
        // Formatted/loaded SHBE files.
        File[] ff = files.getGeneratedSHBEDir().listFiles();
        // Formatted Year Month
        HashSet<ONSPD_YM3> fym3s = new HashSet<>();
        for (File f : ff) {
            if (f.isDirectory()) {
                fym3s.add(new ONSPD_YM3(f.getName()));
            }
        }
        ONSPD_YM3 ym3;
        for (String SHBEFilename : SHBEFilenames) {
            ym3 = getYM3(SHBEFilename);
            if (!fym3s.contains(ym3)) {
                newFilesToRead.add(SHBEFilename);
            }
        }
        ONSPD_YM3 ym30 = getYM3(SHBEFilenames[SHBEFilenames.length - 1]);
        ONSPD_YM3 nYM3 = ONSPD_Handler.getNearestYM3ForONSPDLookup(ym30);
        if (newFilesToRead.size() > 0) {
            Iterator<String> ite = newFilesToRead.iterator();
            while (ite.hasNext()) {
                String SHBEFilename = ite.next();
                SHBE_Records recs;
                recs = new SHBE_Records(Env, logID, dir, SHBEFilename, nYM3);
                Generic_IO.writeObject(recs, recs.getFile());
            }
            writeLookups();
        }
        // Make a backup copy
        File f;
        f = new File(files.getGeneratedLCCDir(), "SHBEBackup");
        if (f.isDirectory()) {
            f = Generic_IO.addToArchive(f, 100);
        } else {
            f = Generic_IO.initialiseArchive(f, 100);
        }
        Generic_IO.copy(files.getGeneratedSHBEDir(), f);
    }

    /**
     * For checking postcodes.
     *
     */
    public void runPostcodeCheckLatest() {
        boolean hoome;
        hoome = true;

        // Declaration
        HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> PostcodeIDPointLookups;
        String YMN;
        String[] SHBEFilenames;
        String SHBEFilename1;

        boolean modifiedAnyRecs;
        String h;

        // Initialisation
        PostcodeToPostcodeIDLookup = getPostcodeToPostcodeIDLookup();
        PostcodeIDPointLookups = getPostcodeIDToPointLookups();
        ClaimIDToClaimRefLookup = getClaimIDToClaimRefLookup();

        modifiedAnyRecs = false;

        // Prepare for output
        SHBEFilenames = getSHBEFilenamesAll();
        SHBEFilename1 = SHBEFilenames[SHBEFilenames.length - 1];
        YMN = getYearMonthNumber(SHBEFilename1);
        ONSPD_YM3 YM31;
        YM31 = getYM3(SHBEFilename1);
        System.out.println("YM31 " + YM31);
        // Nearest YM3 For ONSPD Lookup YM31
        ONSPD_YM3 nyfoly1 = ONSPD_Handler.getNearestYM3ForONSPDLookup(YM31);
        System.out.println("NearestYM3ForONSPDLookupYM31 " + nyfoly1);
        SHBE_Records s1 = new SHBE_Records(Env, logID, YM31);
        HashMap<SHBE_ID, SHBE_Record> recs1;
        recs1 = s1.getRecords(hoome);
        SHBE_Record rec1;
        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup1;
        PostcodeIDToPointLookup1 = PostcodeIDPointLookups.get(nyfoly1);
        // Unique Unmappable Postcodes
        HashSet<String> uup = new HashSet<>();
        // Claimant Postcodes Unmappable
        HashMap<SHBE_ID, String> cpu = s1.getClaimantPostcodesUnmappable();
        SHBE_ID claimID;
        Iterator<SHBE_ID> ite;
        String claimRef;
        ite = cpu.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            claimRef = ClaimIDToClaimRefLookup.get(claimID);
            uup.add(claimRef + "," + cpu.get(claimID));
        }
        // Unique Modified Postcodes
        HashSet<String> ump = new HashSet<>();
        writeOutModifiedPostcodes(ump, YMN, s1,                hoome);

        /**
         * Set up log to write out some basic details of Claims with Claimant
         * Postcodes that are not yet mappable by any means.
         */
        int logIDUP = Env.ge.initLog("UnmappablePostcodes" + YMN, ".csv");
        h = "Ref,Year_Month,ClaimRef,Recorded Postcode,Correct Postcode,"
                + "Input To Academy (Y/N)";
        Env.ge.log(h, logIDUP);
        int ref2 = 1;

        ONSPD_YM3 YM30;
        // Nearest YM3 For ONSPD Lookup YM30
        ONSPD_YM3 nyfolYM30;
        // Claimant Postcodes Unmappable 0
        HashMap<SHBE_ID, String> cpu0;
        SHBE_Records s0;
        HashMap<SHBE_ID, SHBE_Record> recs0;
        SHBE_Record rec0;
        String postcode0;
        String postcode1;
        String postcodef0;
        String unmappablePostcodef0;
        String postcodef1;

        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup0;
        HashMap<SHBE_ID, ONSPD_ID> ClaimIDToPostcodeIDLookup0 = null;
        HashSet<SHBE_ID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;

        //for (int i = SHBEFilenames.length - 2; i >= 0; i--) {
        int i = SHBEFilenames.length - 2;
        // Get previous SHBE.
        YM30 = getYM3(SHBEFilenames[i]);
        System.out.println("YM30 " + YM30);
        YMN = getYearMonthNumber(SHBEFilenames[i]);
        int logID2 = Env.ge.initLog("FutureModifiedPostcodes" + YMN, ".csv");
        h = "ClaimRef,Original Claimant Postcode,Updated from the "
                + "Future Claimant Postcode";
        Env.ge.log(h, logID2);
        nyfolYM30 = ONSPD_Handler.getNearestYM3ForONSPDLookup(YM30);
        Env.ge.log("NearestYM3ForONSPDLookupYM30 " + nyfolYM30);
        s0 = new SHBE_Records(Env, logID, YM30);
        recs0 = s0.getRecords(hoome);
        writeOutModifiedPostcodes(ump, YMN,                s0, hoome);
        PostcodeIDToPointLookup0 = PostcodeIDPointLookups.get(nyfolYM30);
        cpu0 = s0.getClaimantPostcodesUnmappable(hoome);
        boolean modifiedRecs = false;
        ite = cpu0.keySet().iterator();
        HashSet<SHBE_ID> ClaimantPostcodesUnmappable0Remove = new HashSet<>();
        while (ite.hasNext()) {
            claimID = ite.next();
            unmappablePostcodef0 = cpu0.get(claimID);
            claimRef = ClaimIDToClaimRefLookup.get(claimID);
            System.out.println(claimRef);
            rec1 = recs1.get(claimID);
            rec0 = recs0.get(claimID);
            postcodef0 = rec0.getClaimPostcodeF();
            postcode0 = rec0.getDRecord().getClaimantsPostcode();
            if (rec1 != null) {
                postcodef1 = rec1.getClaimPostcodeF();
                if (rec1.isClaimPostcodeFMappable()) {
                    Env.ge.log("Claimants Postcode 0 \"" + postcode0
                            + "\" unmappablePostcodef0 \"" + unmappablePostcodef0
                            + "\" postcodef0 \"" + postcodef0 + "\" changed to "
                            + postcodef1 + " which is mappable.");
                    if (!rec0.ClaimPostcodeFValidPostcodeFormat) {
                        rec0.ClaimPostcodeFUpdatedFromTheFuture = true;
                        rec0.ClaimPostcodeF = postcodef1;
                        rec0.ClaimPostcodeFMappable = true;
                        rec0.ClaimPostcodeFValidPostcodeFormat = true;
                        if (ClaimIDToPostcodeIDLookup0 == null) {
                            ClaimIDToPostcodeIDLookup0 = s0.getClaimIDToPostcodeIDLookup();
                        }
                        ClaimIDToPostcodeIDLookup0.put(claimID, PostcodeToPostcodeIDLookup.get(postcodef1));
                        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 == null) {
                            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = s0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
                        }
                        ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0.add(claimID);
                        ONSPD_ID postcodeID;
                        postcodeID = ClaimIDToPostcodeIDLookup0.get(claimID);
                        ONSPD_Point p;
                        p = PostcodeIDToPointLookup1.get(postcodeID);
                        PostcodeIDToPointLookup0.put(postcodeID, p);
                        modifiedRecs = true;
                        modifiedAnyRecs = true;
                        postcode1 = postcodef1.replaceAll(" ", "");
                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
                        Env.ge.log(claimRef + "," + postcode0 + "," + postcode1, logID);
                        ClaimantPostcodesUnmappable0Remove.add(claimID);
                    }
                } else {
                    System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        postcode1 = postcodef1.replaceAll(" ", "");
//                        if (postcode1.length() > 3) {
//                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
//                        } else {
//                            postcodef1 = rec1.getClaimPostcodeF();
//                        }
                    postcode1 = rec1.getDRecord().getClaimantsPostcode();
                    uup.add(claimRef + "," + postcode1 + ",,");
                    Env.ge.log("" + ref2 + "," + YM31 + "," + claimRef + ","
                            + postcode1 + ",,", logIDUP);
                    ref2++;
//                        System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        UniqueUnmappablePostcodes.add(ClaimRef + "," + postcode0 + ",,");
//                        pw2.println("" + ref2 + "," + YM30 + "," + ClaimRef + "," + postcode0 + ",,");
//                        ref2++;
                }
            }
            // Prepare for next iteration
            recs0 = recs1;
        }
        ite = ClaimantPostcodesUnmappable0Remove.iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            cpu0.remove(claimID);
        }
        if (modifiedRecs == true) {
            // Write out recs0
            Generic_IO.writeObject(cpu0,
                    s0.getClaimantPostcodesUnmappableFile());
            Generic_IO.writeObject(ClaimIDToPostcodeIDLookup0,
                    s0.getClaimIDToPostcodeIDLookupFile());
            Generic_IO.writeObject(recs0, s0.getRecordsFile());
            Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0,
                    s0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());
        }
        Env.ge.closeLog(logIDUP);

        // Write out UniqueUnmappablePostcodes
        h = "ClaimRef,Original Claimant Postcode,Modified Claimant Postcode,"
                + "Input To Academy (Y/N)";
        writeLog("UniqueUnmappablePostcodes", ".csv", h, uup);

        // Write out UniqueModifiedPostcodes
        writeLog("UniqueModifiedPostcodes", ".csv", h, ump);

        // Write out PostcodeIDPointLookups
        if (modifiedAnyRecs == true) {
            Generic_IO.writeObject(PostcodeIDPointLookups,
                    getPostcodeIDToPointLookupsFile());
        }
    }

    /**
     * Write out log.
     *
     * @param n The name of the log to be written.
     * @param e The extension for the filename to be written.
     * @param h The header.
     * @param l The lines.
     */
    protected void writeLog(String n, String e, String h, Collection<String> l) {
        int logID2 = Env.ge.initLog(n, e);
        Env.ge.log(h, logID2, true);
        Env.ge.log(l, logID2, true);
        Env.ge.closeLog(logID2);
    }

    /**
     * For checking postcodes.
     *
     */
    public void runPostcodeCheck() {
        boolean hoome;
        hoome = true;
        // Declaration
        HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> PostcodeIDPointLookups;
        String[] SHBEFilenames;
        String SHBEFilename1;
        String YMN;
        ONSPD_YM3 YM31;
        // Nearest YM3 For ONSPD Lookup YM31
        ONSPD_YM3 nyol;
        SHBE_Records SHBE_Records1;
        HashMap<SHBE_ID, SHBE_Record> recs1;
        SHBE_Record rec1;
        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup1;
        HashSet<String> UniqueUnmappablePostcodes;
        HashMap<SHBE_ID, String> ClaimantPostcodesUnmappable;
        SHBE_ID claimID;
        Iterator<SHBE_ID> ite;
        String claimRef;
        // UniqueModifiedPostcodes
        HashSet<String> ump;
        String h;
        boolean modifiedAnyRecs;

        // Initialisation
        PostcodeToPostcodeIDLookup = getPostcodeToPostcodeIDLookup();
        PostcodeIDPointLookups = getPostcodeIDToPointLookups();
        ClaimIDToClaimRefLookup = getClaimIDToClaimRefLookup();
        SHBEFilenames = getSHBEFilenamesAll();
        SHBEFilename1 = SHBEFilenames[SHBEFilenames.length - 1];
        YMN = getYearMonthNumber(SHBEFilename1);
        YM31 = getYM3(SHBEFilename1);
//        System.out.println("YM31 " + YM31);
        nyol = ONSPD_Handler.getNearestYM3ForONSPDLookup(YM31);
//        System.out.println("NearestYM3ForONSPDLookupYM31 "
//                + NearestYM3ForONSPDLookupYM31);
        SHBE_Records1 = new SHBE_Records(Env, logID, YM31);
        recs1 = SHBE_Records1.getRecords(hoome);
        PostcodeIDToPointLookup1 = PostcodeIDPointLookups.get(nyol);
        UniqueUnmappablePostcodes = new HashSet<>();
        ClaimantPostcodesUnmappable = SHBE_Records1.getClaimantPostcodesUnmappable();
        ump = new HashSet<>();
        modifiedAnyRecs = false;

        // Add to UniqueUnmappablePostcodes
        ite = ClaimantPostcodesUnmappable.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            claimRef = ClaimIDToClaimRefLookup.get(claimID);
            UniqueUnmappablePostcodes.add(claimRef + ","
                    + ClaimantPostcodesUnmappable.get(claimID));
        }

        /**
         * Write out some basic details of Claims with Claimant Postcodes that
         * are not yet mappable by any means.
         */
        int ref2 = 1;
        // More declaration
        ONSPD_YM3 YM30;
        ONSPD_YM3 NearestYM3ForONSPDLookupYM30;
        HashMap<SHBE_ID, String> ClaimantPostcodesUnmappable0;
        SHBE_Records SHBE_Records0;
        HashMap<SHBE_ID, SHBE_Record> recs0;
        SHBE_Record rec0;
        String postcode0;
        String postcode1;
        String postcodef0;
        String unmappablePostcodef0;
        String postcodef1;

        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup0;
        HashMap<SHBE_ID, ONSPD_ID> ClaimIDToPostcodeIDLookup0 = null;
        HashSet<SHBE_ID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;

        for (int i = SHBEFilenames.length - 2; i >= 0; i--) {
            // Get previous SHBE.
            YM30 = getYM3(SHBEFilenames[i]);
            Env.ge.log("YM30 " + YM30);
            YMN = getYearMonthNumber(SHBEFilenames[i]);
            int logID2 = Env.ge.initLog("FutureModifiedPostcodes" + YMN, ".csv");
            h = "ClaimRef,Original Claimant Postcode,Updated from the Future "
                    + "Claimant Postcode";
            Env.ge.log(h, logID2);
            NearestYM3ForONSPDLookupYM30 = ONSPD_Handler.getNearestYM3ForONSPDLookup(YM30);
            System.out.println("NearestYM3ForONSPDLookupYM30 " + NearestYM3ForONSPDLookupYM30);
            SHBE_Records0 = new SHBE_Records(Env, YM30);
            recs0 = SHBE_Records0.getRecords(hoome);
            // <writeOutModifiedPostcodes>
            writeOutModifiedPostcodes(ump, YMN,
                    SHBE_Records0, hoome);
            // </writeOutModifiedPostcodes>
            PostcodeIDToPointLookup0 = PostcodeIDPointLookups.get(NearestYM3ForONSPDLookupYM30);
            // Get previously unmappable postcodes
            ClaimantPostcodesUnmappable0 = SHBE_Records0.getClaimantPostcodesUnmappable(hoome);
            boolean modifiedRecs = false;
            ite = ClaimantPostcodesUnmappable0.keySet().iterator();
            HashSet<SHBE_ID> ClaimantPostcodesUnmappable0Remove = new HashSet<>();
            while (ite.hasNext()) {
                claimID = ite.next();
                unmappablePostcodef0 = ClaimantPostcodesUnmappable0.get(claimID);
                claimRef = ClaimIDToClaimRefLookup.get(claimID);
                //System.out.println(ClaimRef);
                rec1 = recs1.get(claimID);
                rec0 = recs0.get(claimID);
                postcodef0 = rec0.getClaimPostcodeF();
                postcode0 = rec0.getDRecord().getClaimantsPostcode();
                if (rec1 != null) {
                    postcodef1 = rec1.getClaimPostcodeF();
                    if (rec1.isClaimPostcodeFMappable()) {
//                        System.out.println("Claimants Postcode 0 \"" 
//                                + postcode0 + "\" unmappablePostcodef0 \"" 
//                                + unmappablePostcodef0 + "\" postcodef0 \"" 
//                                + postcodef0 + "\" changed to " + postcodef1 
//                                + " which is mappable.");
                        if (!rec0.ClaimPostcodeFValidPostcodeFormat) {
                            rec0.ClaimPostcodeFUpdatedFromTheFuture = true;
                            rec0.ClaimPostcodeF = postcodef1;
                            rec0.ClaimPostcodeFMappable = true;
                            rec0.ClaimPostcodeFValidPostcodeFormat = true;
                            if (ClaimIDToPostcodeIDLookup0 == null) {
                                ClaimIDToPostcodeIDLookup0 = SHBE_Records0.getClaimIDToPostcodeIDLookup();
                            }
                            ONSPD_ID postcodeID = PostcodeToPostcodeIDLookup.get(postcodef1);
                            ClaimIDToPostcodeIDLookup0.put(claimID, postcodeID);
                            if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 == null) {
                                ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = SHBE_Records0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
                            }
                            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0.add(claimID);
                            ONSPD_Point p;
                            p = PostcodeIDToPointLookup1.get(postcodeID);
                            PostcodeIDToPointLookup0.put(postcodeID, p);
                            modifiedRecs = true;
                            modifiedAnyRecs = true;
                            postcode1 = postcodef1.replaceAll(" ", "");
                            postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
                            Env.ge.log(claimRef + "," + postcode0 + "," + postcode1, logID2);
                            ClaimantPostcodesUnmappable0Remove.add(claimID);
                        }
                    } else {
                        Env.ge.log("postcodef1 " + postcodef1 + " is not mappable.");
//                        postcode1 = postcodef1.replaceAll(" ", "");
//                        if (postcode1.length() > 3) {
//                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
//                        } else {
//                            postcodef1 = rec1.getClaimPostcodeF();
//                        }
                        postcode1 = rec1.getDRecord().getClaimantsPostcode();
                        UniqueUnmappablePostcodes.add(claimRef + "," + postcode1 + ",,");
                        Env.ge.log("" + ref2 + "," + YM31 + "," + claimRef
                                + "," + postcode1 + ",,", logID2);
                        ref2++;
//                        System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        UniqueUnmappablePostcodes.add(ClaimRef + "," + postcode0 + ",,");
//                        pw2.println("" + ref2 + "," + YM30 + "," + ClaimRef + "," + postcode0 + ",,");
//                        ref2++;
                    }
                }
            }
            Iterator<SHBE_ID> ite2;
            ite2 = ClaimantPostcodesUnmappable0Remove.iterator();
            while (ite2.hasNext()) {
                claimID = ite2.next();
                ClaimantPostcodesUnmappable0.remove(claimID);
            }
            if (modifiedRecs == true) {
                // Write out recs0
                Generic_IO.writeObject(ClaimantPostcodesUnmappable0,
                        SHBE_Records0.getClaimantPostcodesUnmappableFile());
                Generic_IO.writeObject(ClaimIDToPostcodeIDLookup0,
                        SHBE_Records0.getClaimIDToPostcodeIDLookupFile());
                Generic_IO.writeObject(recs0, SHBE_Records0.getRecordsFile());
                Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0,
                        SHBE_Records0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());
            }
            // Prepare for next iteration
            recs1 = recs0;
            ClaimIDToPostcodeIDLookup0 = null;
            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;
            Env.ge.closeLog(logID2);

            // Write out UniqueUnmappablePostcodes
            h = "ClaimRef,Original Claimant Postcode,Modified Claimant Postcode,"
                    + "Input To Academy (Y/N)";
            writeLog("UniqueUnmappablePostcodes", ".csv", h, UniqueUnmappablePostcodes);

            // Write out UniqueModifiedPostcodes
            writeLog("UniqueModifiedPostcodes", ".csv", h, ump);

            // Write out PostcodeIDPointLookups
            if (modifiedAnyRecs == true) {
                Generic_IO.writeObject(PostcodeIDPointLookups,
                        getPostcodeIDToPointLookupsFile());
            }
        }
    }

    /**
     * Set up a PrintWriter to write out some details of claims with claimant
     * postcodes that are automatically modified in order that they are
     * mappable. The formatting may involve removing any Non A-Z, a-z or 0-9
     * characters. It may also involve changing a "O" to a "0" in the second
     * part of the postcode where a number is expected. And it may also involve
     * removing an additional "0" in the first part of the postcode for example
     * where "LS06" should be "LS6".
     *
     * @param ump Unique Modified Postcodes.
     * @param ymn YearMonthNumber.
     * @param records
     * @param hoome
     */
    protected void writeOutModifiedPostcodes(HashSet<String> ump, String ymn,
            SHBE_Records records, boolean hoome) {
        int ref;
        HashMap<SHBE_ID, String[]> ClaimantPostcodesModified;
        Iterator<SHBE_ID> ite;
        SHBE_ID SHBE_ID;
        String[] postcodes;
        String claimRef;
        int logID2 = Env.ge.initLog("ModifiedPostcodes" + ymn, ".csv");
        String s;
        s = "Ref,ClaimRef,Recorded Postcode,Modified Postcode,Input To Academy (Y/N)";
        Env.ge.log(s);
        ref = 1;
        ClaimantPostcodesModified = records.getClaimantPostcodesModified(hoome);
        ite = ClaimantPostcodesModified.keySet().iterator();
        while (ite.hasNext()) {
            SHBE_ID = ite.next();
            postcodes = ClaimantPostcodesModified.get(SHBE_ID);
            claimRef = ClaimIDToClaimRefLookup.get(SHBE_ID);
            s = claimRef + "," + postcodes[0] + "," + postcodes[1] + ",";
            Env.ge.log("" + ref + "," + s);
            ump.add(s);
            ref++;
        }
        Env.ge.closeLog(logID2);
    }

    /**
     *
     * @param SHBE_Records
     * @param PT
     * @return
     */
    public HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDate(
            SHBE_Records SHBE_Records,
            String PT) {
        HashSet<SHBE_ID> result;
        result = null;
        if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeAll)) {
            result = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateInPayment();
            result.addAll(SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateSuspended());
            result.addAll(SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateOther());
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeIn)) {
            result = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateInPayment();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeSuspended)) {
            result = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateSuspended();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeOther)) {
            result = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateOther();
        }
        return result;
    }

    /**
     *
     * @param SHBE_Records
     * @param PT
     * @return
     */
    public HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDate(
            SHBE_Records SHBE_Records,
            String PT) {
        HashSet<SHBE_ID> result;
        result = null;
        if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeAll)) {
            result = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
            result.addAll(SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateSuspended());
            result.addAll(SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateOther());
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeIn)) {
            result = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeSuspended)) {
            result = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateSuspended();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeOther)) {
            result = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateOther();
        }
        return result;
    }

    public String getClaimantType(SHBE_D_Record D_Record) {
        if (isHBClaim(D_Record)) {
            return SHBE_Strings.s_HB;
        }
        //if (isCTBOnlyClaim(D_Record)) {
        return SHBE_Strings.s_CTB;
        //}
    }

    public ArrayList<String> getClaimantTypes() {
        ArrayList<String> result;
        result = new ArrayList<>();
        result.add(SHBE_Strings.s_HB);
        result.add(SHBE_Strings.s_CTB);
        return result;
    }

    public boolean isCTBOnlyClaimOtherPT(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 0) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaimSuspended(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 2) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaimInPayment(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 1) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaim(SHBE_D_Record D_Record) {
        if (D_Record == null) {
            return false;
        }
        int TT;
        TT = D_Record.getTenancyType();
        return isCTBOnlyClaim(
                TT);
    }

    /**
     * @param TT
     * @return
     */
    public boolean isCTBOnlyClaim(
            int TT) {
        return TT == 5 || TT == 7;
    }

    public boolean isHBClaimOtherPT(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 0) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaimSuspended(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 2) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaimInPayment(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 1) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaim(SHBE_D_Record D_Record) {
        if (D_Record == null) {
            return false;
        }
        int TT;
        TT = D_Record.getTenancyType();
        return isHBClaim(TT);
    }

    public boolean isHBClaim(int TT) {
        if (TT == 5) {
            return false;
        }
        if (TT == 7) {
            return false;
        }
        //return TT > -1 && TT < 10;
        return TT > 0 && TT < 10;
    }

    public HashSet<String> getRecordTypes() {
        return RecordTypes;
    }

    /**
     * Initialises RecordTypes
     */
    public final void initRecordTypes() {
        if (RecordTypes == null) {
            RecordTypes = new HashSet<>();
            RecordTypes.add("A");
            RecordTypes.add("D");
            RecordTypes.add("C");
            RecordTypes.add("R");
            RecordTypes.add("T");
            RecordTypes.add("P");
            RecordTypes.add("G");
            RecordTypes.add("E");
            RecordTypes.add("S");
        }
    }

    HashMap<Integer, ONSPD_YM3> indexYM3s;

    /**
     *
     * @return
     */
    public HashMap<Integer, ONSPD_YM3> getIndexYM3s() {
        if (indexYM3s == null) {
            indexYM3s = new HashMap<>();
            String[] filenames = getSHBEFilenamesAll();
            int i = 0;
            ONSPD_YM3 yM3;
            for (String filename : filenames) {
                yM3 = getYM3(filename);
                indexYM3s.put(i, yM3);
                i++;
            }
        }
        return indexYM3s;
    }

    /**
     *
     * @param S
     * @param StringToSHBE_IDLookup
     * @param SHBE_IDToStringLookup
     * @param list List to add result to if a new one is created.
     * @return
     */
    public SHBE_ID getIDAddIfNeeded(
            String S,
            HashMap<String, SHBE_ID> StringToSHBE_IDLookup,
            HashMap<SHBE_ID, String> SHBE_IDToStringLookup,
            ArrayList<SHBE_ID> list
    ) {
        SHBE_ID result;
        if (StringToSHBE_IDLookup.containsKey(S)) {
            result = StringToSHBE_IDLookup.get(S);
        } else {
            result = new SHBE_ID(SHBE_IDToStringLookup.size());
            SHBE_IDToStringLookup.put(result, S);
            StringToSHBE_IDLookup.put(S, result);
            list.add(result);
        }
        return result;
    }

    /**
     *
     * @param S
     * @param StringToIDLookup
     * @param IDToStringLookup
     * @return
     */
    public SHBE_ID getIDAddIfNeeded(
            String S,
            HashMap<String, SHBE_ID> StringToIDLookup,
            HashMap<SHBE_ID, String> IDToStringLookup
    ) {
        SHBE_ID result;
        if (StringToIDLookup.containsKey(S)) {
            result = StringToIDLookup.get(S);
        } else {
            result = new SHBE_ID(IDToStringLookup.size());
            IDToStringLookup.put(result, S);
            StringToIDLookup.put(S, result);
        }
        return result;
    }

    /**
     * Only called when loading SHBE from source.
     *
     * @param ClaimRef
     * @param ClaimRefToClaimIDLookup
     * @param ClaimIDToClaimRefLookup
     * @param ClaimIDs
     * @param ClaimIDsOfNewSHBEClaims
     * @return
     * @throws java.lang.Exception
     */
    public SHBE_ID getIDAddIfNeeded(
            String ClaimRef,
            HashMap<String, SHBE_ID> ClaimRefToClaimIDLookup,
            HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup,
            HashSet<SHBE_ID> ClaimIDs,
            HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaims
    ) throws Exception {
        SHBE_ID result;
        if (ClaimRefToClaimIDLookup.containsKey(ClaimRef)) {
            result = ClaimRefToClaimIDLookup.get(ClaimRef);
        } else {
            result = new SHBE_ID(ClaimIDToClaimRefLookup.size());
            ClaimIDToClaimRefLookup.put(result, ClaimRef);
            ClaimRefToClaimIDLookup.put(ClaimRef, result);
            if (ClaimIDs.contains(result)) {
                throw new Exception("DRecord already read for ClaimRef " + ClaimRef);
            }
            ClaimIDsOfNewSHBEClaims.add(result);
            ClaimIDs.add(result);
        }
        return result;
    }

    /**
     * Only called when loading SHBE from source.
     *
     * @param PostcodeF
     * @param PostcodeToPostcodeIDLookup
     * @param PostcodeIDToPostcodeLookup
     * @return
     */
    public ONSPD_ID getPostcodeIDAddIfNeeded(String PostcodeF,
            HashMap<String, ONSPD_ID> PostcodeToPostcodeIDLookup,
            HashMap<ONSPD_ID, String> PostcodeIDToPostcodeLookup) {
        ONSPD_ID r;
        if (PostcodeToPostcodeIDLookup.containsKey(PostcodeF)) {
            r = PostcodeToPostcodeIDLookup.get(PostcodeF);
        } else {
            r = new ONSPD_ID(PostcodeIDToPostcodeLookup.size());
//            if (IDToSLookup.size() > Integer.MAX_VALUE) {
//                throw new Error("LookupFiles are full!");
//            }
            PostcodeIDToPostcodeLookup.put(r, PostcodeF);
            PostcodeToPostcodeIDLookup.put(PostcodeF, r);
        }
        return r;
    }

    public SHBE_RecordAggregate aggregate(HashSet<SHBE_Record> records) {
        SHBE_RecordAggregate result = new SHBE_RecordAggregate();
        Iterator<SHBE_Record> ite = records.iterator();
        while (ite.hasNext()) {
            SHBE_Record rec;
            rec = ite.next();
            aggregate(rec, result);
        }
        return result;
    }

    public void aggregate(
            SHBE_Record record,
            SHBE_RecordAggregate a_Aggregate_SHBE_DataRecord) {
        SHBE_D_Record aDRecord;
        aDRecord = record.DRecord;
        a_Aggregate_SHBE_DataRecord.setTotalClaimCount(a_Aggregate_SHBE_DataRecord.getTotalClaimCount() + 1);
        //if (aDRecord.getHousingBenefitClaimReferenceNumber().length() > 2) {
        if (isHBClaim(aDRecord)) {
            a_Aggregate_SHBE_DataRecord.setTotalHBClaimCount(a_Aggregate_SHBE_DataRecord.getTotalHBClaimCount() + 1);
        } else {
            a_Aggregate_SHBE_DataRecord.setTotalCTBClaimCount(a_Aggregate_SHBE_DataRecord.getTotalCTBClaimCount() + 1);
        }
        if (aDRecord.getTenancyType() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType1Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType1Count() + 1);
        }
        if (aDRecord.getTenancyType() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType2Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType2Count() + 1);
        }
        if (aDRecord.getTenancyType() == 3) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType3Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType3Count() + 1);
        }
        if (aDRecord.getTenancyType() == 4) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType4Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType4Count() + 1);
        }
        if (aDRecord.getTenancyType() == 5) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType5Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType5Count() + 1);
        }
        if (aDRecord.getTenancyType() == 6) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType6Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType6Count() + 1);
        }
        if (aDRecord.getTenancyType() == 7) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType7Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType7Count() + 1);
        }
        if (aDRecord.getTenancyType() == 8) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType8Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType8Count() + 1);
        }
        if (aDRecord.getTenancyType() == 9) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType9Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType9Count() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalNumberOfChildDependents(
                a_Aggregate_SHBE_DataRecord.getTotalNumberOfChildDependents()
                + aDRecord.getNumberOfChildDependents());
        a_Aggregate_SHBE_DataRecord.setTotalNumberOfNonDependents(
                a_Aggregate_SHBE_DataRecord.getTotalNumberOfNonDependents()
                + aDRecord.getNumberOfNonDependents());
//        ArrayList<SHBE_S_Record> tSRecords;
//        tSRecords = record.getSRecordsWithoutDRecords();
//        Iterator<SHBE_S_Record> ite;
//        ite = tSRecords.iterator();
//        while (ite.hasNext()) {
//            SHBE_S_Record aSRecord = ite.next();
//            if (aSRecord.getNonDependentStatus() == 0) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus0(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus0() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 1) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus1(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus1() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 2) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus2(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus2() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 3) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus3(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus3() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 4) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus4(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus4() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 5) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus5(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus5() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 6) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus6(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus6() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 7) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus7(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus7() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 8) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus8(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus8() + 1);
//            }
//            a_Aggregate_SHBE_DataRecord.setTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork(
//                    a_Aggregate_SHBE_DataRecord.getTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork()
//                    + aSRecord.getNonDependantGrossWeeklyIncomeFromRemunerativeWork());
//        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 0) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate0(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate0() + 1);
        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate1(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate1() + 1);
        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate2(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate2() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 0) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate0(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate0() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate1(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate1() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate2(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate2() + 1);
        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 1) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 2) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 3) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 4) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 5) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 6) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 1) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 2) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 3) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 4) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 5) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 6) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6() + 1);
//        }
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyHousingBenefitEntitlement(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyHousingBenefitEntitlement()
                + aDRecord.getWeeklyHousingBenefitEntitlement());
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyCouncilTaxBenefitEntitlement(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyCouncilTaxBenefitEntitlement()
                + aDRecord.getWeeklyCouncilTaxBenefitEntitlement());
        if (aDRecord.getLHARegulationsApplied().equalsIgnoreCase("NO")) { // A guess at the values: check!
            a_Aggregate_SHBE_DataRecord.setTotalLHARegulationsApplied0(
                    a_Aggregate_SHBE_DataRecord.getTotalLHARegulationsApplied0()
                    + 1);
        } else {
            //aSHBE_DataRecord.getLHARegulationsApplied() == 1
            a_Aggregate_SHBE_DataRecord.setTotalLHARegulationsApplied1(
                    a_Aggregate_SHBE_DataRecord.getTotalLHARegulationsApplied1()
                    + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyMaximumRent(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyMaximumRent()
                + aDRecord.getWeeklyMaximumRent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsAssessedIncomeFigure()
                + aDRecord.getClaimantsAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsAdjustedAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsAdjustedAssessedIncomeFigure()
                + aDRecord.getClaimantsAdjustedAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalCapital(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalCapital()
                + aDRecord.getClaimantsTotalCapital());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsGrossWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsGrossWeeklyIncomeFromEmployment()
                + aDRecord.getClaimantsGrossWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsNetWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsNetWeeklyIncomeFromEmployment()
                + aDRecord.getClaimantsNetWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsGrossWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsGrossWeeklyIncomeFromSelfEmployment()
                + aDRecord.getClaimantsGrossWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsNetWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsNetWeeklyIncomeFromSelfEmployment()
                + aDRecord.getClaimantsNetWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalAmountOfEarningsDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalAmountOfEarningsDisregarded()
                + aDRecord.getClaimantsTotalAmountOfEarningsDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + aDRecord.getClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromAttendanceAllowance()
                + aDRecord.getClaimantsIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromAttendanceAllowance()
                + aDRecord.getClaimantsIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBusinessStartUpAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBusinessStartUpAllowance()
                + aDRecord.getClaimantsIncomeFromBusinessStartUpAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromChildBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromChildBenefit()
                + aDRecord.getClaimantsIncomeFromChildBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent()
                + aDRecord.getClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromPersonalPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromPersonalPension()
                + aDRecord.getClaimantsIncomeFromPersonalPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromSevereDisabilityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromSevereDisabilityAllowance()
                + aDRecord.getClaimantsIncomeFromSevereDisabilityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromMaternityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromMaternityAllowance()
                + aDRecord.getClaimantsIncomeFromMaternityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance()
                + aDRecord.getClaimantsIncomeFromContributionBasedJobSeekersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStudentGrantLoan()
                + aDRecord.getClaimantsIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStudentGrantLoan()
                + aDRecord.getClaimantsIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromSubTenants(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromSubTenants()
                + aDRecord.getClaimantsIncomeFromSubTenants());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBoarders(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBoarders()
                + aDRecord.getClaimantsIncomeFromBoarders());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromTrainingForWorkCommunityAction(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromTrainingForWorkCommunityAction()
                + aDRecord.getClaimantsIncomeFromTrainingForWorkCommunityAction());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitShortTermLower(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitShortTermLower()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermLower());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermHigher());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitLongTerm(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitLongTerm()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitLongTerm());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit()
                + aDRecord.getClaimantsIncomeFromNewDeal50PlusEmploymentCredit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromNewTaxCredits(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromNewTaxCredits()
                + aDRecord.getClaimantsIncomeFromNewTaxCredits());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent()
                + aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceCareComponent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromGovernemntTraining(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromGovernemntTraining()
                + aDRecord.getClaimantsIncomeFromGovernmentTraining());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit()
                + aDRecord.getClaimantsIncomeFromIndustrialInjuriesDisablementBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromCarersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromCarersAllowance()
                + aDRecord.getClaimantsIncomeFromCarersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStatutoryMaternityPaternityPay(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStatutoryMaternityPaternityPay()
                + aDRecord.getClaimantsIncomeFromStatutoryMaternityPaternityPay());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + aDRecord.getClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP()
                + aDRecord.getClaimantsIncomeFromWarDisablementPensionArmedForcesGIP());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWarMobilitySupplement(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWarMobilitySupplement()
                + aDRecord.getClaimantsIncomeFromWarMobilitySupplement());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowsWidowersPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowsWidowersPension()
                + aDRecord.getClaimantsIncomeFromWarWidowsWidowersPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBereavementAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBereavementAllowance()
                + aDRecord.getClaimantsIncomeFromBereavementAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowedParentsAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowedParentsAllowance()
                + aDRecord.getClaimantsIncomeFromWidowedParentsAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromYouthTrainingScheme(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromYouthTrainingScheme()
                + aDRecord.getClaimantsIncomeFromYouthTrainingScheme());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStatuatorySickPay(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStatuatorySickPay()
                + aDRecord.getClaimantsIncomeFromStatutorySickPay());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsOtherIncome(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsOtherIncome()
                + aDRecord.getClaimantsOtherIncome());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalAmountOfIncomeDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalAmountOfIncomeDisregarded()
                + aDRecord.getClaimantsTotalAmountOfIncomeDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalFamilyPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalFamilyPremiumAwarded()
                + aDRecord.getFamilyPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalFamilyLoneParentPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalFamilyLoneParentPremiumAwarded()
                + aDRecord.getFamilyLoneParentPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalDisabilityPremiumAwarded()
                + aDRecord.getDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalSevereDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalSevereDisabilityPremiumAwarded()
                + aDRecord.getSevereDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalDisabledChildPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalDisabledChildPremiumAwarded()
                + aDRecord.getDisabledChildPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalCarePremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalCarePremiumAwarded()
                + aDRecord.getCarePremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalEnhancedDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalEnhancedDisabilityPremiumAwarded()
                + aDRecord.getEnhancedDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalBereavementPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalBereavementPremiumAwarded()
                + aDRecord.getBereavementPremiumAwarded());
        if (aDRecord.getPartnersStudentIndicator().equalsIgnoreCase("Y")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersStudentIndicator(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersStudentIndicator() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalPartnersAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersAssessedIncomeFigure()
                + aDRecord.getPartnersAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersAdjustedAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersAdjustedAssessedIncomeFigure()
                + aDRecord.getPartnersAdjustedAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersGrossWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersGrossWeeklyIncomeFromEmployment()
                + aDRecord.getPartnersGrossWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersNetWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersNetWeeklyIncomeFromEmployment()
                + aDRecord.getPartnersNetWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersGrossWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersGrossWeeklyIncomeFromSelfEmployment()
                + aDRecord.getPartnersGrossWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersNetWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersNetWeeklyIncomeFromSelfEmployment()
                + aDRecord.getPartnersNetWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalAmountOfEarningsDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalAmountOfEarningsDisregarded()
                + aDRecord.getPartnersTotalAmountOfEarningsDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + aDRecord.getPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromAttendanceAllowance()
                + aDRecord.getPartnersIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBusinessStartUpAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBusinessStartUpAllowance()
                + aDRecord.getPartnersIncomeFromBusinessStartUpAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromChildBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromChildBenefit()
                + aDRecord.getPartnersIncomeFromChildBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromPersonalPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromPersonalPension()
                + aDRecord.getPartnersIncomeFromPersonalPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromSevereDisabilityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromSevereDisabilityAllowance()
                + aDRecord.getPartnersIncomeFromSevereDisabilityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromMaternityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromMaternityAllowance()
                + aDRecord.getPartnersIncomeFromMaternityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromContributionBasedJobSeekersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromContributionBasedJobSeekersAllowance()
                + aDRecord.getPartnersIncomeFromContributionBasedJobSeekersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStudentGrantLoan()
                + aDRecord.getPartnersIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromSubTenants(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromSubTenants()
                + aDRecord.getPartnersIncomeFromSubTenants());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBoarders(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBoarders()
                + aDRecord.getPartnersIncomeFromBoarders());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromTrainingForWorkCommunityAction(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromTrainingForWorkCommunityAction()
                + aDRecord.getPartnersIncomeFromTrainingForWorkCommunityAction());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitShortTermLower(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitShortTermLower()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermLower());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitShortTermHigher(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitShortTermHigher()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermHigher());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitLongTerm(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitLongTerm()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitLongTerm());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit()
                + aDRecord.getPartnersIncomeFromNewDeal50PlusEmploymentCredit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromNewTaxCredits(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromNewTaxCredits()
                + aDRecord.getPartnersIncomeFromNewTaxCredits());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent()
                + aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceCareComponent());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromGovernemntTraining(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromGovernemntTraining()
                + aDRecord.getPartnersIncomeFromGovernmentTraining());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit()
                + aDRecord.getPartnersIncomeFromIndustrialInjuriesDisablementBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromCarersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromCarersAllowance()
                + aDRecord.getPartnersIncomeFromCarersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStatuatorySickPay(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStatuatorySickPay()
                + aDRecord.getPartnersIncomeFromStatutorySickPay());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStatutoryMaternityPaternityPay(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStatutoryMaternityPaternityPay()
                + aDRecord.getPartnersIncomeFromStatutoryMaternityPaternityPay());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + aDRecord.getPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP()
                + aDRecord.getPartnersIncomeFromWarDisablementPensionArmedForcesGIP());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWarMobilitySupplement(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWarMobilitySupplement()
                + aDRecord.getPartnersIncomeFromWarMobilitySupplement());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowsWidowersPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowsWidowersPension()
                + aDRecord.getPartnersIncomeFromWarWidowsWidowersPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBereavementAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBereavementAllowance()
                + aDRecord.getPartnersIncomeFromBereavementAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowedParentsAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowedParentsAllowance()
                + aDRecord.getPartnersIncomeFromWidowedParentsAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromYouthTrainingScheme(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromYouthTrainingScheme()
                + aDRecord.getPartnersIncomeFromYouthTrainingScheme());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersOtherIncome(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersOtherIncome()
                + aDRecord.getPartnersOtherIncome());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalAmountOfIncomeDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalAmountOfIncomeDisregarded()
                + aDRecord.getPartnersTotalAmountOfIncomeDisregarded());
        if (aDRecord.getClaimantsGender().equalsIgnoreCase("F")) {
            a_Aggregate_SHBE_DataRecord.setTotalClaimantsGenderFemale(
                    a_Aggregate_SHBE_DataRecord.getTotalClaimantsGenderFemale() + 1);
        }
        if (aDRecord.getClaimantsGender().equalsIgnoreCase("M")) {
            a_Aggregate_SHBE_DataRecord.setTotalClaimantsGenderMale(
                    a_Aggregate_SHBE_DataRecord.getTotalClaimantsGenderMale() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalContractualRentAmount(
                a_Aggregate_SHBE_DataRecord.getTotalContractualRentAmount()
                + aDRecord.getContractualRentAmount());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromPensionCreditSavingsCredit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromPensionCreditSavingsCredit()
                + aDRecord.getClaimantsIncomeFromPensionCreditSavingsCredit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromPensionCreditSavingsCredit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromPensionCreditSavingsCredit()
                + aDRecord.getPartnersIncomeFromPensionCreditSavingsCredit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromMaintenancePayments(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromMaintenancePayments()
                + aDRecord.getClaimantsIncomeFromMaintenancePayments());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromMaintenancePayments(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromMaintenancePayments()
                + aDRecord.getPartnersIncomeFromMaintenancePayments());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromOccupationalPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromOccupationalPension()
                + aDRecord.getClaimantsIncomeFromOccupationalPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromOccupationalPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromOccupationalPension()
                + aDRecord.getPartnersIncomeFromOccupationalPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowsBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowsBenefit()
                + aDRecord.getClaimantsIncomeFromWidowsBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowsBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowsBenefit()
                + aDRecord.getPartnersIncomeFromWidowsBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalTotalNumberOfRooms(
                a_Aggregate_SHBE_DataRecord.getTotalTotalNumberOfRooms()
                + aDRecord.getTotalNumberOfRooms());
        a_Aggregate_SHBE_DataRecord.setTotalValueOfLHA(
                a_Aggregate_SHBE_DataRecord.getTotalValueOfLHA()
                + aDRecord.getValueOfLHA());
        if (aDRecord.getPartnersGender().equalsIgnoreCase("F")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersGenderFemale(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersGenderFemale() + 1);
        }
        if (aDRecord.getPartnersGender().equalsIgnoreCase("M")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersGenderMale(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersGenderMale() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalTotalAmountOfBackdatedHBAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalTotalAmountOfBackdatedHBAwarded()
                + aDRecord.getTotalAmountOfBackdatedHBAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalTotalAmountOfBackdatedCTBAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalTotalAmountOfBackdatedCTBAwarded()
                + aDRecord.getTotalAmountOfBackdatedCTBAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalCapital(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalCapital()
                + aDRecord.getPartnersTotalCapital());
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure()
                + aDRecord.getWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek()
                + aDRecord.getClaimantsTotalHoursOfRemunerativeWorkPerWeek());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalHoursOfRemunerativeWorkPerWeek(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalHoursOfRemunerativeWorkPerWeek()
                + aDRecord.getPartnersTotalHoursOfRemunerativeWorkPerWeek());
    }

    public long getHouseholdSize(SHBE_Record rec) {
        long result;
        result = 1;
        SHBE_D_Record D_Record;
        D_Record = rec.DRecord;
        result += D_Record.getPartnerFlag();
        int NumberOfChildDependents;
        NumberOfChildDependents = D_Record.getNumberOfChildDependents();
        int NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        int NumberOfDependentsAndNonDependents;
        NumberOfDependentsAndNonDependents = NumberOfChildDependents + NumberOfNonDependents;
        ArrayList<SHBE_S_Record> S_Records;
        S_Records = rec.SRecords;
        if (S_Records != null) {
            result += Math.max(NumberOfDependentsAndNonDependents, S_Records.size());
//            long NumberOfS_Records;
//            NumberOfS_Records = S_Records.size();
//            if (NumberOfS_Records != NumberOfNonDependents ) {
//                rec.init(Env);
//                Iterator<SHBE_S_Record> ite;
//                ite = S_Records.iterator();
//                while (ite.hasNext()) {
//                    SHBE_S_Record S_Record;
//                    S_Record = ite.next();
//                }
//            }
        } else {
            result += NumberOfDependentsAndNonDependents;
        }
        return result;
    }

    public long getHouseholdSizeExcludingPartnerslong(SHBE_D_Record D_Record) {
        long result;
        result = 1;
        result += D_Record.getNumberOfChildDependents();
        long NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        result += NumberOfNonDependents;
        return result;
    }

    public int getHouseholdSizeExcludingPartnersint(SHBE_D_Record D_Record) {
        int result;
        result = 1;
        result += D_Record.getNumberOfChildDependents();
        long NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        result += NumberOfNonDependents;
        return result;
    }

    public long getHouseholdSize(SHBE_D_Record D_Record) {
        long result;
        result = getHouseholdSizeint(D_Record);
        return result;
    }

    public int getHouseholdSizeint(SHBE_D_Record D_Record) {
        int result;
        result = getHouseholdSizeExcludingPartnersint(D_Record);
        result += D_Record.getPartnerFlag();
        return result;
    }

    public long getClaimantsIncomeFromBenefitsAndAllowances(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromAttendanceAllowance();
        result += aDRecord.getClaimantsIncomeFromBereavementAllowance();
        result += aDRecord.getClaimantsIncomeFromBusinessStartUpAllowance();
        result += aDRecord.getClaimantsIncomeFromCarersAllowance();
        result += aDRecord.getClaimantsIncomeFromChildBenefit();
        result += aDRecord.getClaimantsIncomeFromContributionBasedJobSeekersAllowance();
        result += aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceCareComponent();
        result += aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitLongTerm();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermHigher();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermLower();
        result += aDRecord.getClaimantsIncomeFromIndustrialInjuriesDisablementBenefit();
        result += aDRecord.getClaimantsIncomeFromMaternityAllowance();
        result += aDRecord.getClaimantsIncomeFromNewDeal50PlusEmploymentCredit();
        result += aDRecord.getClaimantsIncomeFromNewTaxCredits();
        result += aDRecord.getClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent();
        result += aDRecord.getClaimantsIncomeFromPensionCreditSavingsCredit();
        result += aDRecord.getClaimantsIncomeFromSevereDisabilityAllowance();
        result += aDRecord.getClaimantsIncomeFromStatutoryMaternityPaternityPay();
        result += aDRecord.getClaimantsIncomeFromStatutorySickPay();
        result += aDRecord.getClaimantsIncomeFromWarMobilitySupplement();
        result += aDRecord.getClaimantsIncomeFromWidowedParentsAllowance();
        result += aDRecord.getClaimantsIncomeFromWidowsBenefit();
        return result;
    }

    public long getClaimantsIncomeFromEmployment(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsGrossWeeklyIncomeFromEmployment();
        result += aDRecord.getClaimantsGrossWeeklyIncomeFromSelfEmployment();
        return result;
    }

    public long getClaimantsIncomeFromGovernmentTraining(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromGovernmentTraining();
        result += aDRecord.getClaimantsIncomeFromTrainingForWorkCommunityAction();
        result += aDRecord.getClaimantsIncomeFromYouthTrainingScheme();
        return result;
    }

    public long getClaimantsIncomeFromPensionPrivate(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromOccupationalPension();
        result += aDRecord.getClaimantsIncomeFromPersonalPension();
        return result;
    }

    public long getClaimantsIncomeFromPensionState(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc();
        result += aDRecord.getClaimantsIncomeFromWarDisablementPensionArmedForcesGIP();
        result += aDRecord.getClaimantsIncomeFromWarWidowsWidowersPension();
        return result;
    }

    public long getClaimantsIncomeFromBoardersAndSubTenants(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromSubTenants();
        result += aDRecord.getClaimantsIncomeFromBoarders();
        return result;
    }

    public long getClaimantsIncomeFromOther(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromMaintenancePayments();
        result += aDRecord.getClaimantsIncomeFromStudentGrantLoan();
        result += aDRecord.getClaimantsOtherIncome();
        return result;
    }

    public long getClaimantsIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getClaimantsIncomeFromBenefitsAndAllowances(aDRecord);
        result += getClaimantsIncomeFromEmployment(aDRecord);
        result += getClaimantsIncomeFromGovernmentTraining(aDRecord);
        result += getClaimantsIncomeFromPensionPrivate(aDRecord);
        result += getClaimantsIncomeFromPensionState(aDRecord);
        result += getClaimantsIncomeFromBoardersAndSubTenants(aDRecord);
        result += getClaimantsIncomeFromOther(aDRecord);
        return result;
    }

    public long getHouseholdIncomeTotal(
            SHBE_Record aRecord,
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getClaimantsIncomeTotal(aDRecord);
        result += getPartnersIncomeTotal(aDRecord);
        ArrayList<SHBE_S_Record> SRecords;
        SRecords = aRecord.getSRecords();
        if (SRecords != null) {
            Iterator<SHBE_S_Record> ite;
            ite = SRecords.iterator();
            SHBE_S_Record SHBE_S_Record;
            while (ite.hasNext()) {
                SHBE_S_Record = ite.next();
                result += SHBE_S_Record.getNonDependantGrossWeeklyIncomeFromRemunerativeWork();
            }
        }
        return result;
    }

    public long getPartnersIncomeFromBenefitsAndAllowances(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromAttendanceAllowance();
        result += aDRecord.getPartnersIncomeFromBereavementAllowance();
        result += aDRecord.getPartnersIncomeFromBusinessStartUpAllowance();
        result += aDRecord.getPartnersIncomeFromCarersAllowance();
        result += aDRecord.getPartnersIncomeFromChildBenefit();
        result += aDRecord.getPartnersIncomeFromContributionBasedJobSeekersAllowance();
        result += aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceCareComponent();
        result += aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitLongTerm();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermHigher();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermLower();
        result += aDRecord.getPartnersIncomeFromIndustrialInjuriesDisablementBenefit();
        result += aDRecord.getPartnersIncomeFromMaternityAllowance();
        result += aDRecord.getPartnersIncomeFromNewDeal50PlusEmploymentCredit();
        result += aDRecord.getPartnersIncomeFromNewTaxCredits();
        result += aDRecord.getPartnersIncomeFromPensionCreditSavingsCredit();
        result += aDRecord.getPartnersIncomeFromSevereDisabilityAllowance();
        result += aDRecord.getPartnersIncomeFromStatutoryMaternityPaternityPay();
        result += aDRecord.getPartnersIncomeFromStatutorySickPay();
        result += aDRecord.getPartnersIncomeFromWarMobilitySupplement();
        result += aDRecord.getPartnersIncomeFromWidowedParentsAllowance();
        result += aDRecord.getPartnersIncomeFromWidowsBenefit();
        return result;
    }

    public long getPartnersIncomeFromEmployment(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersGrossWeeklyIncomeFromEmployment();
        result += aDRecord.getPartnersGrossWeeklyIncomeFromSelfEmployment();
        return result;
    }

    public long getPartnersIncomeFromGovernmentTraining(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromGovernmentTraining();
        result += aDRecord.getPartnersIncomeFromTrainingForWorkCommunityAction();
        result += aDRecord.getPartnersIncomeFromYouthTrainingScheme();
        return result;
    }

    public long getPartnersIncomeFromPensionPrivate(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromOccupationalPension();
        result += aDRecord.getPartnersIncomeFromPersonalPension();
        return result;
    }

    public long getPartnersIncomeFromPensionState(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc();
        result += aDRecord.getPartnersIncomeFromWarDisablementPensionArmedForcesGIP();
        result += aDRecord.getPartnersIncomeFromWarWidowsWidowersPension();
        return result;
    }

    public long getPartnersIncomeFromBoardersAndSubTenants(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromSubTenants();
        result += aDRecord.getPartnersIncomeFromBoarders();
        return result;
    }

    public long getPartnersIncomeFromOther(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromMaintenancePayments();
        result += aDRecord.getPartnersIncomeFromStudentGrantLoan();
        result += aDRecord.getPartnersOtherIncome();
        return result;
    }

    public long getPartnersIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getPartnersIncomeFromBenefitsAndAllowances(aDRecord);
        result += getPartnersIncomeFromEmployment(aDRecord);
        result += getPartnersIncomeFromGovernmentTraining(aDRecord);
        result += getPartnersIncomeFromPensionPrivate(aDRecord);
        result += getPartnersIncomeFromPensionState(aDRecord);
        result += getPartnersIncomeFromBoardersAndSubTenants(aDRecord);
        result += getPartnersIncomeFromOther(aDRecord);
        return result;
    }

    public long getClaimantsAndPartnersIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = getClaimantsIncomeTotal(aDRecord) + getPartnersIncomeTotal(aDRecord);
        return result;
    }

    public boolean getUnderOccupancy(
            SHBE_D_Record aDRecord) {
        int numberOfBedroomsForLHARolloutCasesOnly = aDRecord.getNumberOfBedroomsForLHARolloutCasesOnly();
        if (numberOfBedroomsForLHARolloutCasesOnly > 0) {
            if (numberOfBedroomsForLHARolloutCasesOnly
                    > aDRecord.getNumberOfChildDependents()
                    + aDRecord.getNumberOfNonDependents()) {
                return true;
            }
        }
        return false;
    }

    public int getUnderOccupancyAmount(
            SHBE_D_Record aDRecord) {
        int result = 0;
        int numberOfBedroomsForLHARolloutCasesOnly = aDRecord.getNumberOfBedroomsForLHARolloutCasesOnly();
        if (numberOfBedroomsForLHARolloutCasesOnly > 0) {
            result = numberOfBedroomsForLHARolloutCasesOnly
                    - aDRecord.getNumberOfChildDependents()
                    - aDRecord.getNumberOfNonDependents();
        }
        return result;
    }

    /**
     * Method for getting SHBE collections filenames in an array
     *
     * @code {if (SHBEFilenamesAll == null) {
     * String[] list = Env.getFiles().getInputSHBEDir().list();
     * SHBEFilenamesAll = new String[list.length];
     * String s;
     * String ym;
     * TreeMap<String, String> yms;
     * yms = new TreeMap<String, String>();
     * for (String list1 : list) {
     * s = list1;
     * ym = getYearMonthNumber(s);
     * yms.put(ym, s);
     * }
     * Iterator<String> ite; ite = yms.keySet().iterator(); int i = 0; while
     * (ite.hasNext()) { ym = ite.next(); SHBEFilenamesAll[i] = yms.get(ym);
     * i++; } } return SHBEFilenamesAll;} }
     *
     * @return String[] result of SHBE collections filenames
     */
    private String[] SHBEFilenamesAll;

    public int getSHBEFilenamesAllLength() {
        return getSHBEFilenamesAll().length;
    }

    public String[] getSHBEFilenamesAll() {
        if (SHBEFilenamesAll == null) {
            String[] list = Env.files.getInputSHBEDir().list();
            SHBEFilenamesAll = new String[list.length];
            String s;
            String ym;
            TreeMap<String, String> yms;
            yms = new TreeMap<>();
            for (String list1 : list) {
                s = list1;
                ym = getYearMonthNumber(s);
                yms.put(ym, s);
            }
            Iterator<String> ite;
            ite = yms.keySet().iterator();
            int i = 0;
            while (ite.hasNext()) {
                ym = ite.next();
                SHBEFilenamesAll[i] = yms.get(ym);
                i++;
            }
        }
        return SHBEFilenamesAll;
    }

    private ArrayList<ONSPD_YM3> YM3All;

    public ArrayList<ONSPD_YM3> getYM3All() {
        if (YM3All == null) {
            SHBEFilenamesAll = getSHBEFilenamesAll();
            YM3All = new ArrayList<>();
            SHBEFilenamesAll = getSHBEFilenamesAll();
            for (String SHBEFilename : SHBEFilenamesAll) {
                YM3All.add(getYM3(SHBEFilename));
            }
        }
        return YM3All;
    }

    public ArrayList<Integer> getSHBEFilenameIndexes() {
        ArrayList<Integer> result;
        result = new ArrayList<>();
        SHBEFilenamesAll = getSHBEFilenamesAll();
        for (int i = 0; i < SHBEFilenamesAll.length; i++) {
            result.add(i);
        }
        return result;
    }

    /**
     *
     * @param tSHBEFilenames
     * @param include
     * @return * {@code
     * Object[] result;
     * result = new Object[2];
     * TreeMap<BigDecimal, String> valueLabel;
     * valueLabel = new TreeMap<BigDecimal, String>();
     * TreeMap<String, BigDecimal> fileLabelValue;
     * fileLabelValue = new TreeMap<String, BigDecimal>();
     * result[0] = valueLabel;
     * result[1] = fileLabelValue;
     * }
     */
    public Object[] getTreeMapDateLabelSHBEFilenames(String[] tSHBEFilenames,
            ArrayList<Integer> include
    ) {
        // Initialise result r
        Object[] r;
        r = new Object[2];
        TreeMap<BigDecimal, String> valueLabel;
        valueLabel = new TreeMap<>();
        TreeMap<String, BigDecimal> fileLabelValue;
        fileLabelValue = new TreeMap<>();
        r[0] = valueLabel;
        r[1] = fileLabelValue;

        // Get month3Letters lookup
        ArrayList<String> month3Letters;
        month3Letters = Generic_Time.getMonths3Letters();

        // Declare variables
        int startMonth;
        int startYear;
        int yearInt0;
        int month0Int;
        String month0;
        String m30;
        ONSPD_YM3 yM30;
        int i;
        Iterator<Integer> ite;

        // Iterate
        ite = include.iterator();

        // Initialise first
        i = ite.next();
        yM30 = getYM3(tSHBEFilenames[i]);
        yearInt0 = Integer.valueOf(getYear(tSHBEFilenames[i]));
        month0 = getMonth(tSHBEFilenames[i]);
        m30 = month0.substring(0, 3);
        month0Int = month3Letters.indexOf(m30) + 1;
        startMonth = month0Int;
        startYear = yearInt0;

        // Iterate through rest
        while (ite.hasNext()) {
            i = ite.next();
            ONSPD_YM3 yM31;
            yM31 = getYM3(tSHBEFilenames[i]);
            int yearInt;
            String month;
            int monthInt;
            String m3;
            month = getMonth(tSHBEFilenames[i]);
            yearInt = Integer.valueOf(getYear(tSHBEFilenames[i]));
            m3 = month.substring(0, 3);
            monthInt = month3Letters.indexOf(m3) + 1;
            BigDecimal timeSinceStart;
            timeSinceStart = BigDecimal.valueOf(Generic_Time.getMonthDiff(
                    startYear, yearInt, startMonth, monthInt));
            //System.out.println(timeSinceStart);
            String label;
            label = yM30.toString() + "-" + yM31.toString();
            //System.out.println(label);
            valueLabel.put(timeSinceStart, label);
            fileLabelValue.put(label, timeSinceStart);

            // Prepare variables for next iteration
            yM30 = yM31;
        }
        return r;
    }

    /**
     *
     * @param SHBEFilenames
     * @param include
     * @return * {@code
     * Object[] result;
     * result = new Object[2];
     * TreeMap<BigDecimal, String> valueLabel;
     * valueLabel = new TreeMap<BigDecimal, String>();
     * TreeMap<String, BigDecimal> fileLabelValue;
     * fileLabelValue = new TreeMap<String, BigDecimal>();
     * result[0] = valueLabel;
     * result[1] = fileLabelValue;
     * }
     */
    public Object[] getTreeMapDateLabelSHBEFilenamesSingle(
            String[] SHBEFilenames, ArrayList<Integer> include
    ) {
        // Initiailise result r
        Object[] r;
        r = new Object[2];
        TreeMap<BigDecimal, ONSPD_YM3> valueLabel;
        valueLabel = new TreeMap<>();
        TreeMap<ONSPD_YM3, BigDecimal> fileLabelValue;
        fileLabelValue = new TreeMap<>();
        r[0] = valueLabel;
        r[1] = fileLabelValue;

        // Get month3Letters lookup
        ArrayList<String> month3Letters;
        month3Letters = Generic_Time.getMonths3Letters();

        // Declare variables
        int startMonth;
        int startYear;
        ONSPD_YM3 YM3;
        int yearInt;
        String month;
        int monthInt;
        String m3;
        Iterator<Integer> ite;
        int i;

        // Iterate
        ite = include.iterator();

        // Initialise first
        i = ite.next();
        int yearInt0 = Integer.valueOf(getYear(SHBEFilenames[i]));
        String m30 = getMonth3(SHBEFilenames[i]);
        int month0Int = month3Letters.indexOf(m30) + 1;
        startMonth = month0Int;
        startYear = yearInt0;

        // Iterate through rest
        while (ite.hasNext()) {
            i = ite.next();
            YM3 = getYM3(SHBEFilenames[i]);
            month = getMonth(SHBEFilenames[i]);
            yearInt = Integer.valueOf(getYear(SHBEFilenames[i]));
            m3 = month.substring(0, 3);
            monthInt = month3Letters.indexOf(m3) + 1;
            BigDecimal timeSinceStart;
            timeSinceStart = BigDecimal.valueOf(Generic_Time.getMonthDiff(
                    startYear, yearInt, startMonth, monthInt));
            valueLabel.put(timeSinceStart, YM3);
            fileLabelValue.put(YM3, timeSinceStart);
        }
        return r;
    }

//    /**
//     *
//     * @param tSHBEFilenames
//     * @param include
//     * @param startIndex
//     * @return * {@code
//     * Object[] result;
//     * result = new Object[2];
//     * TreeMap<BigDecimal, String> valueLabel;
//     * valueLabel = new TreeMap<BigDecimal, String>();
//     * TreeMap<String, BigDecimal> fileLabelValue;
//     * fileLabelValue = new TreeMap<String, BigDecimal>();
//     * result[0] = valueLabel;
//     * result[1] = fileLabelValue;
//     * }
//     */
//    public TreeMap<BigDecimal, String> getDateValueLabelSHBEFilenames(
//            String[] tSHBEFilenames,
//            ArrayList<Integer> include) {
//        TreeMap<BigDecimal, String> result;
//        result = new TreeMap<BigDecimal, String>();
//        
//        ArrayList<String> month3Letters;
//        month3Letters = Generic_Time.getMonths3Letters();
//
//        int startMonth = 0;
//        int startYear = 0;
//        int yearInt0 = 0;
//        int month0Int = 0;
//        String month0 = "";
//        String m30 = "";
//        String yM30 = "";
//
//        boolean first = true;
//        Iterator<Integer> ite;
//        ite = include.iterator();
//        while (ite.hasNext()) {
//            int i = ite.next();
//            if (first) {
//                yM30 = getYM3(tSHBEFilenames[i]);
//                yearInt0 = Integer.valueOf(getYear(tSHBEFilenames[i]));
//                month0 = getMonth(tSHBEFilenames[i]);
//                m30 = month0.substring(0, 3);
//                month0Int = month3Letters.indexOf(m30) + 1;
//                startMonth = month0Int;
//                startYear = yearInt0;
//                first = false;
//            } else {
//                String yM31 = getYM3(tSHBEFilenames[i]);
//                int yearInt;
//                String month;
//                int monthInt;
//                String m3;
//                month = getMonth(tSHBEFilenames[i]);
//                yearInt = Integer.valueOf(getYear(tSHBEFilenames[i]));
//                m3 = month.substring(0, 3);
//                monthInt = month3Letters.indexOf(m3) + 1;
//                BigDecimal timeSinceStart;
//                timeSinceStart = BigDecimal.valueOf(
//                        Generic_Time.getMonthDiff(
//                                startYear, yearInt, startMonth, monthInt));
//                //System.out.println(timeSinceStart);
//                result.put(
//                        timeSinceStart,
//                        yM30 + " - " + yM31);
//                
//                //System.out.println(fileLabel);
//                yearInt0 = yearInt;
//                month0 = month;
//                m30 = m3;
//                month0Int = monthInt;
//            }
//        }
//        return result;
//    }
    public String getMonth3(String SHBEFilename) {
        String result;
        result = getMonth(SHBEFilename).substring(0, 3);
        return result;
    }

    public ONSPD_YM3 getYM3(String SHBEFilename) {
        return getYM3(SHBEFilename, "_");
    }

    public ONSPD_YM3 getYM3(String SHBEFilename, String separator) {
        ONSPD_YM3 result;
        String year;
        year = getYear(SHBEFilename);
        String m3;
        m3 = getMonth3(SHBEFilename);
        result = new ONSPD_YM3(year + separator + m3);
        return result;
    }

    public String getYM3FromYearMonthNumber(String YearMonth) {
        String result;
        String[] yM;
        yM = YearMonth.split("-");
        String m3;
        m3 = Generic_Time.getMonth3Letters(yM[1]);
        result = yM[0] + SHBE_Strings.symbol_underscore + m3;
        return result;
    }

    public String getYearMonthNumber(String SHBEFilename) {
        String result;
        String year;
        year = getYear(SHBEFilename);
        String monthNumber;
        monthNumber = getMonthNumber(SHBEFilename);
        result = year + "-" + monthNumber;
        return result;
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "May"
     *
     * @param SHBEFilename
     * @return
     */
    public String getMonth(String SHBEFilename) {
        return SHBEFilename.split(" ")[1];
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "May"
     *
     * @param SHBEFilename
     * @return
     */
    public String getMonthNumber(String SHBEFilename) {
        String m3;
        m3 = getMonth3(SHBEFilename);
        return Generic_Time.getMonthNumber(m3);
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "2013"
     *
     * @param SHBEFilename
     * @return
     */
    public String getYear(String SHBEFilename) {
        return SHBEFilename.split(" ")[2].substring(0, 4);
    }

    /**
     * Method for getting SHBE collections filenames in an array
     *
     * @return String[] SHBE collections filenames
     */
    public String[] getSHBEFilenamesSome() {
        String[] result = new String[6];
        result[0] = "hb9991_SHBE_549416k April 2013.csv";
        result[1] = "hb9991_SHBE_555086k May 2013.csv";
        result[2] = "hb9991_SHBE_562036k June 2013.csv";
        result[3] = "hb9991_SHBE_568694k July 2013.csv";
        result[4] = "hb9991_SHBE_576432k August 2013.csv";
        result[5] = "hb9991_SHBE_582832k September 2013.csv";
        return result;
    }

    public HashMap<SHBE_ID, String> getIDToStringLookup(
            File f) {
        HashMap<SHBE_ID, String> result;
        if (f.exists()) {
            result = (HashMap<SHBE_ID, String>) Generic_IO.readObject(f);
        } else {
            result = new HashMap<>();
        }
        return result;
    }

    public int getNumberOfTenancyTypes() {
        return 10;
    }

    public int getNumberOfPassportedStandardIndicators() {
        return 6;
    }

    public int getNumberOfClaimantsEthnicGroups() {
        return 17;
    }

    public int getNumberOfClaimantsEthnicGroupsGrouped() {
        return 10;
    }

    public int getOneOverMaxValueOfPassportStandardIndicator() {
        return 6;
    }

    /**
     * Negation of getOmits()
     *
     * sIncludeAll sIncludeYearly sInclude6Monthly sInclude3Monthly
     * sIncludeMonthly sIncludeMonthlySinceApril2013
     * sInclude2MonthlySinceApril2013Offset0
     * sInclude2MonthlySinceApril2013Offset1 sIncludeStartEndSinceApril2013
     * sIncludeApril2013May2013
     *
     * @return
     */
    public TreeMap<String, ArrayList<Integer>> getIncludes() {
        TreeMap<String, ArrayList<Integer>> result;
        result = new TreeMap<>();
        TreeMap<String, ArrayList<Integer>> omits;
        omits = getOmits();
        Iterator<String> ite;
        ite = omits.keySet().iterator();
        while (ite.hasNext()) {
            String omitKey;
            omitKey = ite.next();
            ArrayList<Integer> omit;
            omit = omits.get(omitKey);
            ArrayList<Integer> include;
            //include = getSHBEFilenameIndexesExcept34();
            include = getSHBEFilenameIndexes();
            include.removeAll(omit);
            result.put(omitKey, include);
        }
        return result;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getOmitAll() {
        return new ArrayList<>();
    }

    public ArrayList<Integer> getIncludeAll() {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmitAll();
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getOmitYearly(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        r.add(1);
        r.add(3);
        r.add(5);
        r.add(6);
        r.add(8);
        r.add(9);
        r.add(10);
        r.add(12);
        r.add(13);
        r.add(14); //Jan 13 NB. Prior to this data not monthly
        r.add(15); //Feb 13
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,29,41,53...
            if (!((i - i0) % 12 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getIncludeYearly(int n) {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmitYearly(n);
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getOmit6Monthly(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        r.add(6);
        r.add(8);
        r.add(10);
        r.add(12);
        r.add(14); //Jan 13 NB. Prior to this data not monthly
        r.add(15); //Feb 13
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,23,29,35,41,47,53...
            if (!((i - i0) % 6 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getInclude6Monthly(int n) {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmit6Monthly(n);
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getOmit3Monthly(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            r.add(i);
        }
        r.add(15); //Feb 13 NB. Prior to this data not monthly
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,20,23,26,29,32,35,38,41,44,47,50,53...
            if (!((i - i0) % 3 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getInclude3Monthly() {
        int n;
        n = getSHBEFilenamesAllLength();
        return getInclude3Monthly(n);
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getInclude3Monthly(int n) {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmit3Monthly(n);
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getOmitMonthly() {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getIncludeMonthly() {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmitMonthly();
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013.
     */
    public ArrayList<Integer> getOmitMonthlyUO() {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2MonthlyUO1(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 17; i < n; i += 2) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2StartEndSinceApril2013(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 18; i < n - 2; i++) {
            r.add(i);
        }
        r.add(n - 1);
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2April2013May2013(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 19; i < n; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 0 months.
     */
    public ArrayList<Integer> getOmit2MonthlyUO0(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 18; i < n; i += 2) {
            r.add(i);
        }
        return r;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getIncludeMonthlyUO() {
        int n;
        n = getSHBEFilenamesAllLength();
        return getIncludeMonthlyUO(n);
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getIncludeMonthlyUO(int n) {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmitMonthlyUO();
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     * Negation of getIncludes(). This method will want modifying if data prior
     * to January 2013 is added.
     *
     * sIncludeAll sIncludeYearly sInclude6Monthly sInclude3Monthly
     * sIncludeMonthly sIncludeMonthlySinceApril2013
     * sInclude2MonthlySinceApril2013Offset0
     * sInclude2MonthlySinceApril2013Offset1 sIncludeStartEndSinceApril2013
     * sIncludeApril2013May2013
     *
     * @return
     */
    public TreeMap<String, ArrayList<Integer>> getOmits() {
        TreeMap<String, ArrayList<Integer>> result;
        result = new TreeMap<>();
        String[] tSHBEFilenames;
        tSHBEFilenames = getSHBEFilenamesAll();
        int n;
        n = tSHBEFilenames.length;
        String omitKey;
        ArrayList<Integer> omitAll;
        omitKey = SHBE_Strings.s_IncludeAll;
        omitAll = getOmitAll();
        result.put(omitKey, omitAll);
        omitKey = SHBE_Strings.s_IncludeYearly;
        ArrayList<Integer> omitYearly;
        omitYearly = getOmitYearly(n);
        result.put(omitKey, omitYearly);
        omitKey = SHBE_Strings.s_Include6Monthly;
        ArrayList<Integer> omit6Monthly;
        omit6Monthly = getOmit6Monthly(n);
        result.put(omitKey, omit6Monthly);
        omitKey = SHBE_Strings.s_Include3Monthly;
        ArrayList<Integer> omit3Monthly;
        omit3Monthly = getOmit3Monthly(n);
        result.put(omitKey, omit3Monthly);
        omitKey = SHBE_Strings.s_IncludeMonthly;
        ArrayList<Integer> omitMonthly;
        omitMonthly = getOmitMonthly();
        result.put(omitKey, omitMonthly);
        omitKey = SHBE_Strings.s_IncludeMonthlySinceApril2013;
        ArrayList<Integer> omitMonthlyUO;
        omitMonthlyUO = getOmitMonthlyUO();
        result.put(omitKey, omitMonthlyUO);
        omitKey = SHBE_Strings.s_Include2MonthlySinceApril2013Offset0;
        ArrayList<Integer> omit2MonthlyUO0;
        omit2MonthlyUO0 = getOmit2MonthlyUO0(n);
        result.put(omitKey, omit2MonthlyUO0);
        omitKey = SHBE_Strings.s_Include2MonthlySinceApril2013Offset1;
        ArrayList<Integer> omit2MonthlyUO1;
        omit2MonthlyUO1 = getOmit2MonthlyUO1(n);
        result.put(omitKey, omit2MonthlyUO1);
        omitKey = SHBE_Strings.s_IncludeStartEndSinceApril2013;
        ArrayList<Integer> omit2StartEndSinceApril2013;
        omit2StartEndSinceApril2013 = getOmit2StartEndSinceApril2013(n);
        result.put(omitKey, omit2StartEndSinceApril2013);
        omitKey = SHBE_Strings.s_IncludeApril2013May2013;
        ArrayList<Integer> omit2April2013May2013;
        omit2April2013May2013 = getOmit2April2013May2013(n);
        result.put(omitKey, omit2April2013May2013);
        return result;
    }

    /**
     *
     * @param yM3
     * @param D_Record
     * @return
     */
    public String getClaimantsAge(String yM3, SHBE_D_Record D_Record) {
        String result;
        String[] syM3;
        syM3 = yM3.split(SHBE_Strings.symbol_underscore);
        result = getClaimantsAge(syM3[0], syM3[1], D_Record);
        return result;
    }

    /**
     *
     * @param year
     * @param month
     * @param D_Record
     * @return
     */
    public String getClaimantsAge(String year, String month,
            SHBE_D_Record D_Record) {
        String result;
        String DoB = D_Record.getClaimantsDateOfBirth();
        result = getAge(year, month, DoB);
        return result;
    }

    /**
     *
     * @param year
     * @param month
     * @param D_Record
     * @return
     */
    public String getPartnersAge(String year, String month,
            SHBE_D_Record D_Record) {
        String result;
        String DoB = D_Record.getPartnersDateOfBirth();
        result = getAge(year, month, DoB);
        return result;
    }

    public String getAge(
            String year,
            String month,
            String DoB) {
        if (DoB == null) {
            return "";
        }
        if (DoB.isEmpty()) {
            return DoB;
        }
        String result;
        String[] sDoB = DoB.split("/");
        Generic_Time tDoB;
        tDoB = new Generic_Time(Integer.valueOf(sDoB[0]),
                Integer.valueOf(sDoB[1]), Integer.valueOf(sDoB[2]));
        Generic_Time tNow;
        tNow = new Generic_Time(0, Integer.valueOf(month), Integer.valueOf(year));
        result = Integer.toString(Generic_Time.getAgeInYears(tNow, tDoB));
        return result;
    }

    /**
     *
     * @param D_Record
     * @return true iff there is any disability awards in the household of
     * D_Record.
     */
    public boolean getDisability(SHBE_D_Record D_Record) {
        // Disability
        int DisabilityPremiumAwarded = D_Record.getDisabilityPremiumAwarded();
        int SevereDisabilityPremiumAwarded = D_Record.getSevereDisabilityPremiumAwarded();
        int DisabledChildPremiumAwarded = D_Record.getDisabledChildPremiumAwarded();
        int EnhancedDisabilityPremiumAwarded = D_Record.getEnhancedDisabilityPremiumAwarded();
        // General Household Disability Flag
        return DisabilityPremiumAwarded == 1
                || SevereDisabilityPremiumAwarded == 1
                || DisabledChildPremiumAwarded == 1
                || EnhancedDisabilityPremiumAwarded == 1;
    }

    public int getEthnicityGroup(SHBE_D_Record D_Record) {
        int claimantsEthnicGroup = D_Record.getClaimantsEthnicGroup();
        switch (claimantsEthnicGroup) {
            case 1:
                return 1;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            case 8:
                return 6;
            case 9:
                return 6;
            case 10:
                return 6;
            case 11:
                return 6;
            case 12:
                return 7;
            case 13:
                return 7;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
        }
        return 0;
    }

    public String getEthnicityName(SHBE_D_Record D_Record) {
        int claimantsEthnicGroup = D_Record.getClaimantsEthnicGroup();
        switch (claimantsEthnicGroup) {
            case 1:
                return "White: British";
            case 2:
                return "White: Irish";
            case 3:
                return "White: Any Other";
            case 4:
                return "Mixed: White and Black Caribbean";
            case 5:
                return "Mixed: White and Black African";
            case 6:
                return "Mixed: White and Asian";
            case 7:
                return "Mixed: Any Other";
            case 8:
                return "Asian or Asian British: Indian";
            case 9:
                return "Asian or Asian British: Pakistani";
            case 10:
                return "Asian or Asian British: Bangladeshi";
            case 11:
                return "Asian or Asian British: Any Other";
            case 12:
                return "Black or Black British: Caribbean";
            case 13:
                return "Black or Black British: African";
            case 14:
                return "Black or Black British: Any Other";
            case 15:
                return "Chinese";
            case 16:
                return "Any Other";
        }
        return "";
    }

    public String getEthnicityGroupName(int ethnicityGroup) {
        switch (ethnicityGroup) {
            case 1:
                return "WhiteBritish_Or_WhiteIrish";
            case 2:
                return "WhiteOther";
            case 3:
                return "MixedWhiteAndBlackAfrican_Or_MixedWhiteAndBlackCaribbean";
            case 4:
                return "MixedWhiteAndAsian";
            case 5:
                return "MixedOther";
            case 6:
                return "Asian_Or_AsianBritish";
            case 7:
                return "BlackOrBlackBritishCaribbean_Or_BlackOrBlackBritishAfrican_Or_BlackOrBlackBritishOther";
            case 8:
                return "Chinese";
            case 9:
                return "Other";
        }
        return "";
    }

    /**
     *
     * @param d
     * @return
     */
    public SHBE_PersonID getClaimantPersonID(SHBE_D_Record d) {
        SHBE_PersonID result;
        SHBE_ID NINO_ID;
        NINO_ID = getNINOToNINOIDLookup().get(d.getClaimantsNationalInsuranceNumber());
        SHBE_ID DOB_ID;
        DOB_ID = getDOBToDOBIDLookup().get(d.getClaimantsDateOfBirth());
        result = new SHBE_PersonID(NINO_ID, DOB_ID);
        return result;
    }

    /**
     *
     * @param d
     * @return
     */
    public SHBE_PersonID getPartnerPersonID(SHBE_D_Record d) {
        SHBE_PersonID r;
        SHBE_ID NINO_ID;
        NINO_ID = getNINOToNINOIDLookup().get(d.getPartnersNationalInsuranceNumber());
        SHBE_ID DOB_ID;
        DOB_ID = getDOBToDOBIDLookup().get(d.getPartnersDateOfBirth());
        r = new SHBE_PersonID(NINO_ID, DOB_ID);
        return r;
    }

    /**
     *
     * @param S_Record
     * @return
     */
    public SHBE_PersonID getNonDependentPersonID(SHBE_S_Record S_Record) {
        SHBE_PersonID result;
        SHBE_ID NINO_ID;
        NINO_ID = getNINOToNINOIDLookup().get(
                S_Record.getSubRecordChildReferenceNumberOrNINO());
        SHBE_ID DOB_ID;
        DOB_ID = getDOBToDOBIDLookup().get(S_Record.getSubRecordDateOfBirth());
        result = new SHBE_PersonID(NINO_ID, DOB_ID);
        return result;
    }

    /**
     *
     * @param S_Record
     * @param index
     * @return
     */
    public SHBE_PersonID getDependentPersonID(SHBE_S_Record S_Record,
            int index) {
        SHBE_PersonID result;
        String NINO;
        String ClaimantsNINO;
        SHBE_ID NINO_ID;
        SHBE_ID DOB_ID;
        NINO = S_Record.getSubRecordChildReferenceNumberOrNINO();
        ClaimantsNINO = S_Record.getClaimantsNationalInsuranceNumber();
        if (ClaimantsNINO.trim().isEmpty()) {
            ClaimantsNINO = SHBE_Strings.s_DefaultNINO;
            Env.ge.log("ClaimantsNINO is empty for "
                    + "ClaimRef " + S_Record.getCouncilTaxBenefitClaimReferenceNumber()
                    + " Setting as default NINO " + ClaimantsNINO, true);
        }
        if (NINO.isEmpty()) {
            NINO = "" + index;
            NINO += "_" + ClaimantsNINO;
        } else {
            NINO += "_" + ClaimantsNINO;
        }
        NINO_ID = getNINOToNINOIDLookup().get(NINO);
        DOB_ID = getDOBToDOBIDLookup().get(S_Record.getSubRecordDateOfBirth());
        result = new SHBE_PersonID(NINO_ID, DOB_ID);
        return result;
    }

    /**
     *
     * @param S_Records
     * @return
     */
    public HashSet<SHBE_PersonID> getPersonIDs(
            ArrayList<SHBE_S_Record> S_Records) {
        HashSet<SHBE_PersonID> result;
        result = new HashSet<>();
        SHBE_S_Record S_Record;
        SHBE_ID NINO_ID;
        SHBE_ID DOB_ID;
        Iterator<SHBE_S_Record> ite;
        ite = S_Records.iterator();
        while (ite.hasNext()) {
            S_Record = ite.next();
            NINO_ID = getNINOToNINOIDLookup().get(
                    S_Record.getSubRecordChildReferenceNumberOrNINO());
            DOB_ID = getDOBToDOBIDLookup().get(
                    S_Record.getSubRecordDateOfBirth());

            result.add(new SHBE_PersonID(NINO_ID, DOB_ID));
        }
        return result;
    }

    /**
     *
     * @param S_Record
     * @return
     */
    public SHBE_PersonID getNonDependentPersonIDs(SHBE_S_Record S_Record) {
        SHBE_PersonID result;
        SHBE_ID NINO_ID;
        SHBE_ID DOB_ID;
        NINO_ID = getNINOToNINOIDLookup().get(
                S_Record.getSubRecordChildReferenceNumberOrNINO());
        DOB_ID = getDOBToDOBIDLookup().get(S_Record.getSubRecordDateOfBirth());
        result = new SHBE_PersonID(NINO_ID, DOB_ID);
        return result;
    }

    /**
     * For getting a DW_PersonID for the NINO and DOB given. If the NINOID
     * and/or the DOBID do not already exist, these are added to
     * NINOToNINOIDLookup and NINOIDToNINOLookup, and/or DOBToDOBIDLookup and
     * DOBIDToDOBLookup respectfully.
     *
     * @param NINO
     * @param DOB
     * @param NINOToNINOIDLookup
     * @param NINOIDToNINOLookup
     * @param DOBToDOBIDLookup
     * @param DOBIDToDOBLookup
     * @return
     */
    SHBE_PersonID getPersonID(String NINO, String DOB,
            HashMap<String, SHBE_ID> NINOToNINOIDLookup,
            HashMap<SHBE_ID, String> NINOIDToNINOLookup,
            HashMap<String, SHBE_ID> DOBToDOBIDLookup,
            HashMap<SHBE_ID, String> DOBIDToDOBLookup) {
        SHBE_ID NINOID;
        NINOID = getIDAddIfNeeded(NINO, NINOToNINOIDLookup, NINOIDToNINOLookup);
        SHBE_ID DOBID;
        DOBID = getIDAddIfNeeded(DOB, DOBToDOBIDLookup, DOBIDToDOBLookup);
        return new SHBE_PersonID(NINOID, DOBID);
    }

    /**
     * For getting a DW_PersonID for the DRecord. If the NINOID and/or the DOBID
     * for the DRecord do not already exist, these are added to
     * NINOToNINOIDLookup and NINOIDToNINOLookup, and/or DOBToDOBIDLookup and
     * DOBIDToDOBLookup respectfully.
     *
     * @param DRecord
     * @param NINOToNINOIDLookup
     * @param NINOIDToNINOLookup
     * @param DOBToDOBIDLookup
     * @param DOBIDToDOBLookup
     * @return
     */
    SHBE_PersonID getPersonID(SHBE_D_Record DRecord,
            HashMap<String, SHBE_ID> NINOToNINOIDLookup,
            HashMap<SHBE_ID, String> NINOIDToNINOLookup,
            HashMap<String, SHBE_ID> DOBToDOBIDLookup,
            HashMap<SHBE_ID, String> DOBIDToDOBLookup) {
        String NINO;
        NINO = DRecord.getPartnersNationalInsuranceNumber();
        String DOB;
        DOB = DRecord.getPartnersDateOfBirth();
        return SHBE_Handler.this.getPersonID(NINO, DOB, NINOToNINOIDLookup,
                NINOIDToNINOLookup, DOBToDOBIDLookup, DOBIDToDOBLookup);
    }

    public HashSet<SHBE_PersonID> getUniquePersonIDs(
            HashMap<SHBE_ID, HashSet<SHBE_PersonID>> ClaimIDToPersonIDsLookup) {
        HashSet<SHBE_PersonID> result;
        Collection<HashSet<SHBE_PersonID>> c;
        Iterator<HashSet<SHBE_PersonID>> ite2;
        result = new HashSet<>();
        c = ClaimIDToPersonIDsLookup.values();
        ite2 = c.iterator();
        while (ite2.hasNext()) {
            result.addAll(ite2.next());
        }
        return result;
    }

    public HashSet<SHBE_PersonID> getUniquePersonIDs0(
            HashMap<SHBE_ID, SHBE_PersonID> ClaimIDToPersonIDLookup) {
        HashSet<SHBE_PersonID> result;
        result = new HashSet<>();
        result.addAll(ClaimIDToPersonIDLookup.values());
        return result;
    }
}
