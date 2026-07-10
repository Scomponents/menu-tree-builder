package com.intechcore.scomponents.fx.menubuilder.config;

import com.intechcore.scomponents.fx.menubuilder.command.ICommandGroup;
import com.intechcore.scomponents.fx.menubuilder.control.icon.IIcon;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A proxy implementation of {@link IToolboxCommandConfig} that decorates an existing command configuration
 * to filter out specific nested commands from its hierarchy.
 * <p>
 * This class applies a filter to the command tree, ensuring that any command present in the provided
 * removal set is excluded from the results of {@link #getNestedCommands()}. The filtering is applied
 * recursively down the tree by wrapping child commands in new proxy instances.
 */
public class ProxyCommandConfig implements IToolboxCommandConfig {

    /** The original command configuration being wrapped */
    private final IToolboxCommandConfig node;

    /** The set of command configurations to exclude from the nested commands hierarchy */
    private final Set<IToolboxCommandConfig> removeSet;

    /**
     * Constructs a new ProxyCommandConfig.
     *
     * @param node      the original command configuration to wrap
     * @param removeSet the set of command configurations to exclude from the nested commands hierarchy
     */
    public ProxyCommandConfig(IToolboxCommandConfig node, Set<IToolboxCommandConfig> removeSet) {
        this.node = node;
        this.removeSet = removeSet;
    }

    /**
     * {@inheritDoc}
     * <p>Delegates the call to the wrapped origin node
     */
    @Override
    public IIcon getIcon() {
        return this.node.getIcon();
    }

    /**
     * {@inheritDoc}
     * <p>Delegates the call to the wrapped origin node
     */
    @Override
    public ControlType getControlType() {
        return this.node.getControlType();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the nested commands of the wrapped node, applying the following transformations:
     * <ul>
     *     <li>Filters out {@code null} values</li>
     *     <li>Filters out any commands present in the removal set</li>
     *     <li>Recursively wraps the remaining child commands in a new {@code ProxyCommandConfig}
     *         to ensure the filtering is applied to deeper levels of the tree</li>
     * </ul>
     *
     * @return a stream of filtered and proxied nested commands, or {@code null} if the origin node has no nested commands
     */
    @Override
    public Stream<IToolboxCommandConfig> getNestedCommands() {
        if (node.getNestedCommands() == null) {
            return null;
        }
        return node.getNestedCommands()
                .filter(Objects::nonNull)
                .filter(child -> !removeSet.contains(child))
                .map(child -> (IToolboxCommandConfig) new ProxyCommandConfig(child, removeSet))
                .collect(Collectors.toList())
                .stream();
    }

    /**
     * {@inheritDoc}
     * <p>Delegates the call to the wrapped origin node
     */
    @Override
    public <TCommandGroup extends Enum<TCommandGroup>> ICommandGroup<TCommandGroup> getToggleGroup() {
        return this.node.getToggleGroup();
    }

    /**
     * {@inheritDoc}
     * <p>Delegates the hash code calculation to the wrapped origin node
     */
    @Override
    public int hashCode() {
        return this.node.hashCode();
    }

    /**
     * {@inheritDoc}
     * <p>Delegates the equality check to the wrapped origin node
     */
    @Override
    public boolean equals(Object obj) {
        return this.node.equals(obj);
    }

    /**
     * Retrieves the original, unwrapped command configuration
     *
     * @return the underlying origin node
     */
    public IToolboxCommandConfig getOrigin() {
        return this.node;
    }

    /**
     * Creates a filtered stream of command configurations by wrapping the provided roots
     * in a {@code ProxyCommandConfig} that excludes the specified elements
     * <p>
     * If the collection of elements to remove is {@code null} or empty, the original stream of roots
     * is returned without any wrapping or filtering
     *
     * @param roots            the collection of root command configurations to process
     * @param elementsToRemove the collection of command configurations to exclude from the tree
     * @return a stream of proxied root commands with the specified elements filtered out of their hierarchies
     */
    public static Stream<IToolboxCommandConfig> filterTree(
            Collection<IToolboxCommandConfig> roots,
            Collection<IToolboxCommandConfig> elementsToRemove) {

        if (elementsToRemove == null || elementsToRemove.isEmpty()) {
            return roots.stream();
        }

        Set<IToolboxCommandConfig> removeSet = new HashSet<>(elementsToRemove);

        return roots.stream()
                .filter(Objects::nonNull)
                .filter(root -> !removeSet.contains(root))
                .map(root -> new ProxyCommandConfig(root, removeSet));
    }
}
