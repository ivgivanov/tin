package info.ivgivanov.tin.utils;

import java.util.ArrayList;
import java.util.List;

import com.vmware.vim25.SelectionSpec;
import com.vmware.vim25.TraversalSpec;

public class CollectionUtils {
	
	
	public static List<SelectionSpec> buildFullTraversal() {
        // Terminal traversal specs

        // RP -> VM
        TraversalSpec rpToVm = new TraversalSpec();
        rpToVm.setName("rpToVm");
        rpToVm.setType("ResourcePool");
        rpToVm.setPath("vm");
        rpToVm.setSkip(Boolean.FALSE);

        // vApp -> VM
        TraversalSpec vAppToVM = new TraversalSpec();
        vAppToVM.setName("vAppToVM");
        vAppToVM.setType("VirtualApp");
        vAppToVM.setPath("vm");

        // HostSystem -> VM
        TraversalSpec hToVm = new TraversalSpec();
        hToVm.setType("HostSystem");
        hToVm.setPath("vm");
        hToVm.setName("hToVm");
        hToVm.getSelectSet().add(getSelectionSpec("VisitFolders"));
        hToVm.setSkip(Boolean.FALSE);

        // DC -> DS
        TraversalSpec dcToDs = new TraversalSpec();
        dcToDs.setType("Datacenter");
        dcToDs.setPath("datastore");
        dcToDs.setName("dcToDs");
        dcToDs.setSkip(Boolean.FALSE);

        // DC -> DSFolder
        TraversalSpec dcToDsFolder = new TraversalSpec();
        dcToDsFolder.setType("Datacenter");
        dcToDsFolder.setPath("datastoreFolder");
        dcToDsFolder.setName("dcToDsFolder");
        dcToDsFolder.setSkip(Boolean.FALSE);

        // Recurse through all ResourcePools
        TraversalSpec rpToRp = new TraversalSpec();
        rpToRp.setType("ResourcePool");
        rpToRp.setPath("resourcePool");
        rpToRp.setSkip(Boolean.FALSE);
        rpToRp.setName("rpToRp");
        rpToRp.getSelectSet().add(getSelectionSpec("rpToRp"));

        TraversalSpec crToRp = new TraversalSpec();
        crToRp.setType("ComputeResource");
        crToRp.setPath("resourcePool");
        crToRp.setSkip(Boolean.FALSE);
        crToRp.setName("crToRp");
        crToRp.getSelectSet().add(getSelectionSpec("rpToRp"));

        TraversalSpec crToH = new TraversalSpec();
        crToH.setSkip(Boolean.FALSE);
        crToH.setType("ComputeResource");
        crToH.setPath("host");
        crToH.setName("crToH");

        TraversalSpec dcToHf = new TraversalSpec();
        dcToHf.setSkip(Boolean.FALSE);
        dcToHf.setType("Datacenter");
        dcToHf.setPath("hostFolder");
        dcToHf.setName("dcToHf");
        dcToHf.getSelectSet().add(getSelectionSpec("VisitFolders"));

        TraversalSpec vAppToRp = new TraversalSpec();
        vAppToRp.setName("vAppToRp");
        vAppToRp.setType("VirtualApp");
        vAppToRp.setPath("resourcePool");
        vAppToRp.getSelectSet().add(getSelectionSpec("rpToRp"));

        TraversalSpec dcToVmf = new TraversalSpec();
        dcToVmf.setType("Datacenter");
        dcToVmf.setSkip(Boolean.FALSE);
        dcToVmf.setPath("vmFolder");
        dcToVmf.setName("dcToVmf");
        dcToVmf.getSelectSet().add(getSelectionSpec("VisitFolders"));

        // For Folder -> Folder recursion
        TraversalSpec visitFolders = new TraversalSpec();
        visitFolders.setType("Folder");
        visitFolders.setPath("childEntity");
        visitFolders.setSkip(Boolean.FALSE);
        visitFolders.setName("VisitFolders");

        List<SelectionSpec> sspecarrvf = new ArrayList<SelectionSpec>();
        sspecarrvf.add(getSelectionSpec("crToRp"));
        sspecarrvf.add(getSelectionSpec("crToH"));
        sspecarrvf.add(getSelectionSpec("dcToVmf"));
        sspecarrvf.add(getSelectionSpec("dcToHf"));
        sspecarrvf.add(getSelectionSpec("vAppToRp"));
        sspecarrvf.add(getSelectionSpec("vAppToVM"));
        sspecarrvf.add(getSelectionSpec("dcToDs"));
        sspecarrvf.add(getSelectionSpec("dcToDsFolder"));
        sspecarrvf.add(getSelectionSpec("hToVm"));
        sspecarrvf.add(getSelectionSpec("rpToVm"));
        sspecarrvf.add(getSelectionSpec("VisitFolders"));

        visitFolders.getSelectSet().addAll(sspecarrvf);

        List<SelectionSpec> resultspec = new ArrayList<SelectionSpec>();
        resultspec.add(visitFolders);
        resultspec.add(crToRp);
        resultspec.add(crToH);
        resultspec.add(dcToVmf);
        resultspec.add(dcToHf);
        resultspec.add(vAppToRp);
        resultspec.add(vAppToVM);
        resultspec.add(dcToDs);
        resultspec.add(dcToDsFolder);
        resultspec.add(hToVm);
        resultspec.add(rpToVm);
        resultspec.add(rpToRp);

        return resultspec;
    }

    public static SelectionSpec getSelectionSpec(String name) {
        SelectionSpec genericSpec = new SelectionSpec();
        genericSpec.setName(name);
        return genericSpec;
    }

}
