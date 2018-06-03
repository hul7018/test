package studiplayer.audio;


import java.io.File;

public abstract class AudioFile 
{
	protected String pathname;
	protected String filename;
	protected String author;
	protected String title;
	
	private final static String sepchar = String.valueOf(File.separatorChar);
	
	public AudioFile()
	{
		
	}
	
	public AudioFile(String s) throws NotPlayableException
	{
		parsePathname(s);
		File f = new File(this.getPathname());
		if(!f.canRead())
		{
			throw new NotPlayableException(s, "wrong");
		}
	}
	
	public void parsePathname(String pathname)
	{
		this.pathname = pathname.replaceAll("\\\\+", sepchar).replaceAll("/+", sepchar).replaceAll(":", "");
		if(this.pathname.length() > 1 )
		{	
			if(Character.isUpperCase(this.pathname.charAt(0)) && String.valueOf(this.pathname.charAt(1)).equals(sepchar))
			{
				String word = String.valueOf(this.pathname.charAt(0));
				String newWord = sepchar + word;
				this.pathname = this.pathname.replaceAll(word, newWord);
			}
		}
		parseFilename(this.pathname);
	}
	
	public String getPathname()
	{
		return this.pathname;
	}
	
	public void parseFilename(String filename)
	{
		String name;
		if(filename.lastIndexOf(sepchar) < 0)
		{
			this.filename = filename;
			name = this.filename;
		}
		else
		{
			if(filename.lastIndexOf(sepchar) == filename.length()-1)
			{
				this.filename = "";
			}
			else
			{
				String split[] = filename.split(sepchar);	
				this.filename = split[split.length-1];
			}
			name = this.filename.trim();
		}
		
		
		
		if(name.equals(""))
		{
			this.author = name;
			this.title = name;
		}
		else if(name.length() == 1 && name.equals("-"))
		{
			this.author = "";
			this.title = name;
		}
		else
		{
			int index = this.filename.lastIndexOf(" - ");
			int period = this.filename.lastIndexOf(".");
			
			if(index >= 0)
			{
				this.author = this.filename.substring(0, index).trim();
			}
			else
			{
				this.author = "";
			}
			
			if(period >= 0 && index >= 0)
			{
				this.title = this.filename.substring(index+3, period).trim();
			}
			else if(index >= 0 && period < 0)
			{
				if(this.filename.length() > 1)
				{
					this.title = "";
				}
				else
				{
					this.title = this.filename;
				}
			}
			else if(index < 0 && period < 0)
			{
				this.title = "";
			}
			else
			{
				this.title = this.filename.substring(0, period);
			}
		}
	}
	
	public String getFilename()
	{
		return this.filename;	
	}
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String toString() 
	{
		if(getAuthor().isEmpty() == true)
		{
			return getTitle();
		}
		else
		{
			return getAuthor() + " - " +getTitle();
		}
	}
	
	public abstract void play() throws NotPlayableException;
	
	public abstract void togglePause();
	
	public abstract void stop();
	
	public abstract String getFormattedDuration();
	
	public abstract String getFormattedPosition();
	
	public abstract String[] fields();
	
}
