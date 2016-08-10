/*Copyright 2016 Agileworks*/
package nl.agiletech.flow.project.types;

import nl.agiletech.flow.project.inspect.AssetInspector;
import nl.agiletech.flow.project.inspect.BiosInspector;
import nl.agiletech.flow.project.inspect.DhcpInspector;
import nl.agiletech.flow.project.inspect.DiskInspector;
import nl.agiletech.flow.project.inspect.Ec2MetadataInspector;
import nl.agiletech.flow.project.inspect.Ec2UserdataInspector;
import nl.agiletech.flow.project.inspect.FileSystemInspector;
import nl.agiletech.flow.project.inspect.GceMetadataInspector;
import nl.agiletech.flow.project.inspect.IdentityInspector;
import nl.agiletech.flow.project.inspect.KernelInspector;
import nl.agiletech.flow.project.inspect.MemoryInspector;
import nl.agiletech.flow.project.inspect.MountPointInspector;
import nl.agiletech.flow.project.inspect.NetworkInspector;
import nl.agiletech.flow.project.inspect.OperatingSystemInspector;
import nl.agiletech.flow.project.inspect.PackageVersionInspector;
import nl.agiletech.flow.project.inspect.PartitionInspector;
import nl.agiletech.flow.project.inspect.PathInspector;
import nl.agiletech.flow.project.inspect.ProcessorInspector;
import nl.agiletech.flow.project.inspect.SshInspector;
import nl.agiletech.flow.project.inspect.SysCtlInspector;
import nl.agiletech.flow.project.inspect.SystemProfileInspector;
import nl.agiletech.flow.project.inspect.SystemUptimeInspector;
import nl.agiletech.flow.project.inspect.TimezoneInspector;
import nl.agiletech.flow.project.inspect.VirtualMachineInspector;

abstract class AbstractNode extends Task implements TakesContext {
	// begin inspectors
	// TODO: make these abstract so subclass can override?
	public PackageVersionInspector inspectorVersion = new PackageVersionInspector("agiletech-flow-bot");
	public IdentityInspector identity = new IdentityInspector();
	public DiskInspector disks = new DiskInspector();
	public FileSystemInspector filesystems = new FileSystemInspector();
	public SysCtlInspector sysCtl = new SysCtlInspector();
	public Ec2MetadataInspector ec2Metadata = new Ec2MetadataInspector();
	public Ec2UserdataInspector ec2Userdata = new Ec2UserdataInspector();
	public GceMetadataInspector gceMetadata = new GceMetadataInspector();
	public VirtualMachineInspector vm = new VirtualMachineInspector();
	public KernelInspector kernel = new KernelInspector();
	public MemoryInspector memory = new MemoryInspector();
	public MountPointInspector mountPoints = new MountPointInspector();
	public NetworkInspector network = new NetworkInspector();
	public OperatingSystemInspector os = new OperatingSystemInspector();
	public PartitionInspector partitions = new PartitionInspector();
	public PathInspector path = new PathInspector();
	public ProcessorInspector processors = new ProcessorInspector();
	public SshInspector ssh = new SshInspector();
	public SystemProfileInspector systemProfile = new SystemProfileInspector();
	public SystemUptimeInspector systemUptime = new SystemUptimeInspector();
	public TimezoneInspector timezone = new TimezoneInspector();
	public BiosInspector bios = new BiosInspector();
	public AssetInspector assets = new AssetInspector();
	public DhcpInspector dhcp = new DhcpInspector();
	// end inspectors

	public AbstractNode() {
		super(false);
	}

	@Override
	public String getVersion() {
		return "default";
	}
}
