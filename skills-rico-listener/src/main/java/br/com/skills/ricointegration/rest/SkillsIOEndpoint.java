package br.com.skills.ricointegration.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.mysql.fabric.xmlrpc.base.Data;

import br.com.skills.ricointegration.entity.Papel;
import br.com.skills.ricointegration.service.SkillsIOService;

@Path("/files")
public class SkillsIOEndpoint {

	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://gleidson/skills/files/";

	@Context
	private ServletContext context;
	
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> formParts = input.getFormDataMap();

		List<InputPart> inPart = formParts.get("file");
		List<String> papeis = new ArrayList<>();
		
		
		for (InputPart inputPart : inPart) {
			try {
				// Retrieve headers, read the Content-Disposition header to
				// obtain the original name of the file
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				fileName = parseFileName(headers);
				// Handle the body of that part with an InputStream
				InputStream istream = inputPart.getBody(InputStream.class, null);
				InputStreamReader isr = new InputStreamReader(istream);
				BufferedReader br = new BufferedReader(isr);
				String s = br.readLine(); // primeira linha
				while (s != null) {
					papeis.add(s);
					s = br.readLine();
				}
				br.close();
				fileName = SERVER_UPLOAD_LOCATION_FOLDER + fileName;
				saveFile(istream, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		SkillsIOService buideData = new SkillsIOService();
		buideData.setPapeis(papeis);
		Thread exectute = new Thread(buideData);
		exectute.start();
		String output = "File saved to server location : " + fileName;

		return Response.status(200).entity(output).build();
	}

	// Parse Content-Disposition header to get the original file name
	private String parseFileName(MultivaluedMap<String, String> headers) {

		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"", "");
				return fileName;
			}
		}
		return "randomName";
	}

	// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {
		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
				return;
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/script")
	@Produces("text/plain")
	public Response getFile() {

		InputStream is = SkillsIOEndpoint.class.getResourceAsStream("/script-skills-rico.sql");

		StringBuilder tabela = new StringBuilder("CREATE TABLE [CONSOL_#{PAPEL}] (\n\t");
		tabela.append("[ID] INT, [CORRETORA] NUMERIC(18, 0),\n\t[COMPROU] NUMERIC(18, 0), [VENDEU] NUMERIC(18, 0),\n\t");
		tabela.append("[DATA DA LEITURA] DATETIME, [ACUMULAÇÃO] NUMERIC(18, 0))");
		
		StringBuilder script = new StringBuilder();
		SkillsIOService service = new SkillsIOService();
		
		List<Papel> precos = service.recuperarPapeis(new Data());
		
		
		ResponseBuilder response = Response.ok((Object) is);
		response.header("Content-Disposition", "attachment; filename=\"script.sql\"");
		return response.build();

	}
	
	
}