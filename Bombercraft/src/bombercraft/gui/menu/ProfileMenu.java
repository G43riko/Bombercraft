package bombercraft.gui.menu;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import bombercraft.gui.component.Button;
import utils.math.GVector2f;

public class ProfileMenu extends Menu{
	private ArrayList<String> availableProfiles = loadProfiles(); 
	private MainMenu parent;
	
	public ProfileMenu(MainMenu parent) {
		super(parent.getCoreGame());
		this.parent = parent;
		init();
	}

	@Override
	public void doAct(GVector2f click) {
		if(parent.getActMenu() == MainMenu.OPTIONS_PROFILE){
			availableProfiles.stream().forEach(a -> {
				if(components.get(a).isClickIn(click))
					selectProfile(a);
			});
			if(components.get("createProfile").isClickIn(click))
				createProfile();
			if(components.get("back").isClickIn(click))
				parent.setMainMenu();
		}
	}

	@Override
	public void calcPosition() {
		
	}

	@Override
	protected void init() {
		availableProfiles.stream().forEach(a -> addComponent(a, new Button(this,a)));
		
		addComponent("createProfile", new Button(this, "vytvorit novy profil"));
		components.get("createProfile").setDisable(true);
		
		addComponent("back", new Button(this, "Naspa"));
		components.get("back").setDisable(true);
	}

	private void selectProfile(String profile){
		parent.getCoreGame().setProfile(profile);
		parent.setMainMenu();
		components.get("back").setDisable(false);
	}
	
	private void createProfile(){
		System.out.println("create profile: ");
	}
	
	private ArrayList<String> loadProfiles(){
		ArrayList<String> result = new ArrayList<String>(); 
		URL url = ProfileMenu.class.getResource("/profiles/");
		
		if (url != null){
			try {
				File dir = new File(url.toURI());
				
			    for (File nextFile : dir.listFiles())
			    	result.add(nextFile.getName().replace(".txt", ""));
			    
			    return result;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
