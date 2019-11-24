package edu.ucsb.cs56.mapache_search.preview;

import edu.ucsb.cs56.mapache_search.search.Item;
import edu.ucsb.cs56.mapache_search.stackexchange.StackExchangeQueryService;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service("previewProvider")
public class PreviewProviderServiceImpl implements PreviewProviderService {
    private final StackExchangeQueryService queryService;

    public PreviewProviderServiceImpl(StackExchangeQueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    public String getPreviewType(Item item) {
        if (queryService.supportedSites().contains(item.getDisplayLink())) {
            try {
                URL url = new URL(item.getLink());

                // only preview questions, not other stack overflow pages
                if (url.getPath().split("/")[1].equalsIgnoreCase("questions")) {
                    return "stackexchange";
                }
            } catch (MalformedURLException ignored) {
                // if we can't parse the url, we don't care about it anyway
            }
        }

        return "default";
    }
}
