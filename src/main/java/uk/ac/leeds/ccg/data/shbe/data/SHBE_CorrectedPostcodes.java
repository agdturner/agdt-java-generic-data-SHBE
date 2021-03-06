/*
 * Copyright (C) 2016 geoagdt.
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
package uk.ac.leeds.ccg.data.shbe.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import uk.ac.leeds.ccg.data.format.Data_ReadTXT;
import uk.ac.leeds.ccg.data.ukp.data.UKP_Data;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_CorrectedPostcodes extends SHBE_Object {

    protected final transient UKP_Data Postcode_Handler;

    private HashMap<String, HashSet<String>> UnmappableToMappablePostcodes;
    private HashMap<String, ArrayList<String>> ClaimRefToOriginalPostcodes;
    private HashMap<String, ArrayList<String>> ClaimRefToCorrectedPostcodes;
    private HashSet<String> PostcodesCheckedAsMappable;

    public SHBE_CorrectedPostcodes(SHBE_Environment env) {
        super(env);
        Postcode_Handler = env.oe.getHandler();
        files = env.files;
    }

    public static void main(String[] args) {
//        new SHBE_CorrectedPostcodes(new SHBE_Environment(0)).run();
    }

    /**
     * Currently this reads in Postcodes2016-10.csv. These are postcodes that
     * have been checked in Academy at Leeds City Council and either corrected,
     * or checked and found to be mappable.
     *
     * @throws java.io.IOException If encountered.
     */
    public void run() throws IOException {
        Path dir = Paths.get(files.getInputLCCDir().toString(),
                "AcademyPostcodeCorrections");
        Path f = Paths.get(dir.toString(), "Postcodes2016-10.csv");
        //Generic_ReadCSV.testRead(f, dir, 6);
        Data_ReadTXT reader = new Data_ReadTXT(env.de);
        ArrayList<String> lines = reader.read(f, dir, 6);
        Iterator<String> ite = lines.iterator();
        ite.next(); // Skip header
        String[] split;
        String ClaimRef;
        String OriginalPostcode;
        String CorrectedPostcode;
        String OriginalPostcodeF;
        String CorrectedPostcodeF;

        UnmappableToMappablePostcodes = new HashMap<>();
        ClaimRefToOriginalPostcodes = new HashMap<>();
        ClaimRefToCorrectedPostcodes = new HashMap<>();
        PostcodesCheckedAsMappable = new HashSet<>();

        while (ite.hasNext()) {
            String s = ite.next();
            split = s.split(",");
            ClaimRef = split[1].trim();
            OriginalPostcode = split[2];
            OriginalPostcodeF = Postcode_Handler.formatPostcode(OriginalPostcode);
            CorrectedPostcode = split[3];
            CorrectedPostcodeF = Postcode_Handler.formatPostcode(CorrectedPostcode);
//            for (int i = 0; i < split.length; i ++ ){
//                System.out.print(split[i].trim() + " ");
//            }
            if (OriginalPostcodeF.equalsIgnoreCase(CorrectedPostcodeF)) {
                //System.out.println(ClaimRef + ", " + OriginalPostcode + ", " + CorrectedPostcode + ", N");
                PostcodesCheckedAsMappable.add(OriginalPostcodeF);
            } else {
                //System.out.println(ClaimRef + ", " + OriginalPostcode + ", " + CorrectedPostcode + ", Y");
                HashSet<String> MappablePostcodes;
                if (UnmappableToMappablePostcodes.containsKey(OriginalPostcodeF)) {
                    MappablePostcodes = UnmappableToMappablePostcodes.get(OriginalPostcodeF);
                } else {
                    MappablePostcodes = new HashSet<>();
                    UnmappableToMappablePostcodes.put(OriginalPostcodeF, MappablePostcodes);
                }
                MappablePostcodes.add(CorrectedPostcodeF);
                ArrayList<String> OriginalPostcodes;
                OriginalPostcodes = new ArrayList<>();
                OriginalPostcodes.add(OriginalPostcodeF);
                getClaimRefToOriginalPostcodes().put(ClaimRef, OriginalPostcodes);
                ArrayList<String> CorrectedPostcodes;
                CorrectedPostcodes = new ArrayList<>();
                CorrectedPostcodes.add(CorrectedPostcodeF);
                getClaimRefToCorrectedPostcodes().put(ClaimRef, CorrectedPostcodes);
            }
        }
        env.env.log("UnmappableToMappablePostcode.size() "
                + UnmappableToMappablePostcodes.size(), logID, true);
        String m;
        m = "UnmappableToMappablePostcode";
        env.env.log("<" + m + ">", logID, false);
        env.env.log("Unmappable,Mappable", logID, false);
        ite = UnmappableToMappablePostcodes.keySet().iterator();
        while (ite.hasNext()) {
            OriginalPostcodeF = ite.next();
            String s = OriginalPostcodeF;
            HashSet<String> CorrectedPostcodes;
            CorrectedPostcodes = getUnmappableToMappablePostcodes().get(
                    OriginalPostcodeF);
            Iterator<String> ite2;
            ite2 = CorrectedPostcodes.iterator();
            while (ite2.hasNext()) {
                CorrectedPostcodeF = ite2.next();
                s += "," + CorrectedPostcodeF;
            }
            env.env.log(s, logID, false);
        }
        env.env.log("</" + m + ">", logID, false);

        m = "PostcodesCheckedAsMappable";
        env.env.log("<" + m + ">", logID, false);
        env.env.log("Mappable", logID, false);
        ite = getPostcodesCheckedAsMappable().iterator();
        while (ite.hasNext()) {
            OriginalPostcodeF = ite.next();
            env.env.log(OriginalPostcodeF, logID, false);
        }
        env.env.log("</" + m + ">", logID, false);

        Path dirout;
        dirout = files.getGeneratedLCCDir();
        Path fout;
        fout = Paths.get(dirout.toString(), "DW_CorrectedPostcodes"
                + SHBE_Strings.s_BinaryFileExtension);
        Generic_IO.writeObject(this, fout);
    }

    /**
     * @return the UnmappableToMappablePostcode
     */
    public HashMap<String, HashSet<String>> getUnmappableToMappablePostcodes() {
        return UnmappableToMappablePostcodes;
    }

    /**
     * @return the ClaimRefToOriginalPostcode
     */
    public HashMap<String, ArrayList<String>> getClaimRefToOriginalPostcodes() {
        return ClaimRefToOriginalPostcodes;
    }

    /**
     * @return the ClaimRefToCorrectedPostcode
     */
    public HashMap<String, ArrayList<String>> getClaimRefToCorrectedPostcodes() {
        return ClaimRefToCorrectedPostcodes;
    }

    /**
     * @return the PostcodesCheckedAsMappable
     */
    public HashSet<String> getPostcodesCheckedAsMappable() {
        return PostcodesCheckedAsMappable;
    }

}
