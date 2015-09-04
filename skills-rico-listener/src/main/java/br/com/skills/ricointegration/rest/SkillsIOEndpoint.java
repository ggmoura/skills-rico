package br.com.skills.ricointegration.rest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import br.com.skills.ricointegration.entity.PrecoMedio;
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
	public Response getFile() throws IOException {	
		StringBuilder script = new StringBuilder();
		
		SkillsIOService service = new SkillsIOService();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		List<PrecoMedio> precos = service.recuperarPapeis(new Data());
		StringBuilder insert = null;
		List<String> papeis = new ArrayList<>();
		String codePapel = null;
		
		for (PrecoMedio papel : precos) {
			codePapel = papel.getPapelCorretora().getPapel().getCode();
			if (!papeis.contains(codePapel)) {
				script.append("DELETE FROM CONSOL_").append(codePapel).append(";\n");				
			}
			papeis.add(codePapel);
		}
		
		script.append("\n\n\n");
		
		
		
		for (PrecoMedio precoMedio : precos) {
			insert = new StringBuilder();
			insert.append("INSERT INTO [BolsaAnalise].[dbo].[CONSOL_");
			insert.append(precoMedio.getPapelCorretora().getPapel().getCode()).append("] ([ID], [CORRETORA], ");
			insert.append("[COMPROU], [VENDEU], [DATA DA LEITURA] ,[ACUMULAÇÃO]) VALUES ( ");
			insert.append(precoMedio.getId()).append(", ");
			insert.append(precoMedio.getPapelCorretora().getCorretora().getCodigo()).append(", ");
			insert.append(precoMedio.getCompra()).append(", ");
			insert.append(precoMedio.getVenda()).append(", ");
			insert.append("'").append(df.format(precoMedio.getDataAconpanhamento())).append("', ");
			insert.append("null").append(");\n");
			script.append(insert);
		}
		
		byte[] stringByte = script.toString().getBytes();
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(script.length());
	    bos.write(stringByte);
		ResponseBuilder response = Response.ok((Object) bos);
		response.header("Content-Disposition", "attachment; filename=\"script.sql\"");
		return response.build();
	}
}